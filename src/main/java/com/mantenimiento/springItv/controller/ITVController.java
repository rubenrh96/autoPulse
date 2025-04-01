package com.mantenimiento.springItv.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mantenimiento.springItv.entities.CategoriaEntity;
import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.entities.ItvEntity;
import com.mantenimiento.springItv.entities.MantenimientoEntity;
import com.mantenimiento.springItv.entities.NeumaticoEntity;
import com.mantenimiento.springItv.entities.RecambioEntity;
import com.mantenimiento.springItv.entities.RepostajeEntity;
import com.mantenimiento.springItv.models.Coche;
import com.mantenimiento.springItv.models.Itv;
import com.mantenimiento.springItv.models.Mantenimiento;
import com.mantenimiento.springItv.models.Neumatico;
import com.mantenimiento.springItv.models.Recambio;
import com.mantenimiento.springItv.models.Repostaje;
import com.mantenimiento.springItv.services.CategoriaService;
import com.mantenimiento.springItv.services.CocheService;
import com.mantenimiento.springItv.services.ItvService;
import com.mantenimiento.springItv.services.MantenimientoService;
import com.mantenimiento.springItv.services.NeumaticoService;
import com.mantenimiento.springItv.services.RecambioService;
import com.mantenimiento.springItv.services.RepostajeService;
import com.mantenimiento.springItv.transformadores.TransformadorCoche;
import com.mantenimiento.springItv.transformadores.TransformadorItv;
import com.mantenimiento.springItv.transformadores.TransformadorMantenimiento;
import com.mantenimiento.springItv.transformadores.TransformadorNeumatico;
import com.mantenimiento.springItv.transformadores.TransformadorRepostaje;

@Controller
@RequestMapping("/")
public class ITVController {

	@Autowired
    private CocheService cocheService;
	
	@Autowired
	private NeumaticoService neumaticoService;
	
	@Autowired
	private RepostajeService repostajeService;
	
	@Autowired
	private MantenimientoService mantenimientoService;
	
	@Autowired
	private ItvService itvService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private RecambioService recambioService;
	
	@GetMapping("/coches")
	public String listarCoches(Model coche) {
		List<CocheEntity> listaCoches = cocheService.listarCoches();
		List<Coche> listaCochesModelo = new ArrayList<>();
		for (CocheEntity cocheEntity : listaCoches) {
			Coche cocheModel = TransformadorCoche.cocheEntityToCoche(cocheEntity);
			listaCochesModelo.add(cocheModel);
		}
		
		List<RecambioEntity> listaRecambios = recambioService.listarRecambios();
		coche.addAttribute("coches", listaCochesModelo);
		coche.addAttribute("recambios", listaRecambios);
		return "coche/listaCoches";
	}
	
	@PostMapping("coches/{matricula}")
    public String updateKilometraje(@PathVariable("matricula") String matricula, @RequestParam("kilometros") int kilometros) {
	Optional<CocheEntity> coche = cocheService.obtenerPorId(matricula);
	if (coche.isPresent()) {
		CocheEntity cocheEntity = coche.get();
		cocheEntity.setKilometros(kilometros);
        cocheService.guardarCoche(cocheEntity);
        ResponseEntity.ok().build();
    }else {
    	ResponseEntity.notFound().build();
    }
	return "redirect:/coches/" + matricula;
}
	
	@GetMapping("/coches/{matricula}")
    public String verDetalles(@PathVariable("matricula") String matricula, Model model) {
        Optional<CocheEntity> coche = cocheService.obtenerPorId(matricula);
        if (coche.isPresent()) {
            model.addAttribute("coche", coche.get());
        }
        /**Neumaticos**/
        List<NeumaticoEntity> listaNeumaticos = neumaticoService.listarNeumaticos(matricula);
        listaNeumaticos.sort(Comparator.comparing(NeumaticoEntity::getFechaMontaje).reversed());
        List<Neumatico> listaNeumaticosModel = new ArrayList<>();
        double precioTotalNeumaticos = 0;
        int contadorNeumaticos = 0;
        for (NeumaticoEntity neumaticoEntity : listaNeumaticos) {
			Neumatico neumatico = TransformadorNeumatico.neumaticoEntityToNeumatico(neumaticoEntity);
			if(contadorNeumaticos < 4) {
				contadorNeumaticos += neumatico.getNumero();
				listaNeumaticosModel.add(neumatico);
			}
			precioTotalNeumaticos += neumatico.getPrecio();
		}
        if(!listaNeumaticosModel.isEmpty()) {
        	model.addAttribute("neumaticos", listaNeumaticosModel);
        	model.addAttribute("precioTotalNeumaticos", precioTotalNeumaticos);
        }
        
        /**Repostajes**/
        List<RepostajeEntity> listaRepostajes = repostajeService.listarRepostajes(matricula);
        List<Repostaje> listaRepostajesModel = new ArrayList<>();
        double precioTotalRepostajes = 0;
        for (RepostajeEntity repostajeEntity : listaRepostajes) {
        	Repostaje repostaje = TransformadorRepostaje.repostajeEntityToRepostaje(repostajeEntity);
        	listaRepostajesModel.add(repostaje);
        	precioTotalRepostajes += repostaje.getPrecio();
        }
        listaRepostajesModel.sort(Comparator.comparing(Repostaje::getFecha).reversed());
        if(!listaRepostajesModel.isEmpty()) {
        	model.addAttribute("repostaje", listaRepostajesModel.get(0));
        	model.addAttribute("precioTotalRepostajes", precioTotalRepostajes);
        }
        
        /**Mantenimientos**/
        List<MantenimientoEntity> listaMantenimientos = mantenimientoService.listarMantenimientos(matricula);
        List<Mantenimiento> listaMantenimientosModel = new ArrayList<>();
        double precioTotalMantenimientos = 0;
        for (MantenimientoEntity mantenimientoEntity : listaMantenimientos) {
        	Mantenimiento mantenimiento = TransformadorMantenimiento.mantenimientoEntityToMantenimiento(mantenimientoEntity);
        	listaMantenimientosModel.add(mantenimiento);
        	precioTotalMantenimientos += mantenimiento.getPrecio();
        }
        listaMantenimientosModel.sort(Comparator.comparing(Mantenimiento::getFecha).reversed());
        if(!listaMantenimientosModel.isEmpty()) {
        	model.addAttribute("precioTotalMantenimientos", precioTotalMantenimientos);
	        model.addAttribute("mantenimiento", listaMantenimientosModel.get(0));
        }
        
        /**Itv**/
        List<ItvEntity> listaItvs = itvService.listarItvs(matricula);
        List<Itv> listaItvsModel = new ArrayList<>();
        double precioTotal = 0;
        for (ItvEntity itvEntity : listaItvs) {
        	Itv itv = TransformadorItv.itvEntityToItv(itvEntity);
        	listaItvsModel.add(itv);
        	precioTotal +=  itvEntity.getPrecio();
        }
        listaItvsModel.sort(Comparator.comparing(Itv::getKmRevision).reversed());
        if(!listaItvsModel.isEmpty()) {
        	model.addAttribute("precioTotal", precioTotal);
        	model.addAttribute("itv", listaItvsModel.get(0));
        }

        return "coche/detallesCoche";
    }
	
    @PostMapping("/coches")
    public ResponseEntity<CocheEntity> crearCoche(@RequestBody CocheEntity coche) {
        CocheEntity nuevoCoche = cocheService.guardarCoche(coche);
        return new ResponseEntity<>(nuevoCoche, HttpStatus.CREATED);
    }
    
    /****************************************/
    /**********   NEUMATICOS   **************/
    /****************************************/
    
    @GetMapping("/neumaticos/{matricula}")
    public String mostrarFormularioDeAlta(@PathVariable("matricula") String matricula, Model model) {
        model.addAttribute("neumatico", new Neumatico());
        model.addAttribute(matricula);
        return "neumatico/agregarNeumatico";
    }
    
    @PostMapping("/neumaticos/{matricula}")
    public String crearNeumatico(NeumaticoEntity neumatico, @RequestParam("matricula") String matricula) {
    	Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);
        if (cocheOpt.isPresent()) {
            neumatico.setCoche(cocheOpt.get());
        }
    	neumaticoService.guardarNeumatico(neumatico);
    	return "redirect:/coches/" + matricula;
    }
    
    @GetMapping("/eliminarNeumatico/{id}")
    public String eliminarNeumatico(@PathVariable("id") Integer id) {
        neumaticoService.eliminarNeumatico(id);
        return "redirect:/coches";
    }
    
    /****************************************/
    /**********   REPOSTAJES   **************/
    /****************************************/
    
    @GetMapping("/repostajes/{matricula}")
    public String mostrarFormularioDeAltaRepostaje(@PathVariable("matricula") String matricula, Model model) {
    	model.addAttribute("repostaje", new Repostaje());
    	model.addAttribute(matricula);
    	return "repostaje/agregarRepostaje";
    }
    
    @PostMapping("/repostajes/{matricula}")
    public String crearRepostaje(RepostajeEntity repostaje, @RequestParam("matricula") String matricula) {
    	Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);
    	if (cocheOpt.isPresent()) {
    		repostaje.setCoche(cocheOpt.get());
    	}
    	repostajeService.guardarRepostaje(repostaje);
    	return "redirect:/coches/" + matricula;
    }
    
    @GetMapping("/eliminarRepostaje/{id}")
    public String eliminarRepostaje(@PathVariable("id") Integer id) {
    	repostajeService.eliminarRepostaje(id);
    	return "redirect:/coches";
    }
    
    @GetMapping("/visualizarRepostaje/{idRepostaje}")
    public String buscarRepostajePorId(@PathVariable("idRepostaje") Integer idRepostaje, Model model) {
    	 Optional<RepostajeEntity> repostaje = repostajeService.obtenerPorId(idRepostaje);
         if (repostaje.isPresent()) {
        	 String matricula = repostaje.get().getCoche().getMatricula();
             model.addAttribute("repostaje", repostaje.get());
             model.addAttribute("matricula", matricula);
         }
    	return "repostaje/visualizarRepostaje";
    }
    
    @GetMapping("/repostajes/lista/{matricula}")
    public String listarRepostajes(@PathVariable("matricula") String matricula, Model model) {
    	List<RepostajeEntity> listaRepostajeEntity = repostajeService.listarRepostajes(matricula);
    	List<Repostaje> listaRepostajes = new ArrayList<>();
    	for (RepostajeEntity repostajeEntity : listaRepostajeEntity) {
    		Repostaje repostaje = TransformadorRepostaje.repostajeEntityToRepostaje(repostajeEntity);
    		listaRepostajes.add(repostaje);
        }
    	listaRepostajes.sort(Comparator.comparing(Repostaje::getFecha).reversed());
    	model.addAttribute("listaRepostajes", listaRepostajes);
    	model.addAttribute(matricula);
    	return "repostaje/listaRepostaje";
    }
    /****************************************/
    /**********   MANTENIMIENTO   ***********/
    /****************************************/
    
    @GetMapping("/mantenimientos/lista/{matricula}")
    public String listarMantenientos(@PathVariable("matricula") String matricula, Model model) {
    	List<MantenimientoEntity> listaMantenimientoEntity = mantenimientoService.listarMantenimientos(matricula);
    	List<Mantenimiento> listaMantenimiento = new ArrayList<>();
    	for (MantenimientoEntity mantenimientoEntity : listaMantenimientoEntity) {
    		Mantenimiento mantenimiento = TransformadorMantenimiento.mantenimientoEntityToMantenimiento(mantenimientoEntity);
    		listaMantenimiento.add(mantenimiento);
        }
    	listaMantenimiento.sort(Comparator.comparing(Mantenimiento::getFecha).reversed());
    	model.addAttribute("listaMantenimiento", listaMantenimiento);
    	model.addAttribute(matricula);
    	return "mantenimiento/listaMantenimiento";
    }
    
    @GetMapping("/mantenimientos/{matricula}")
    public String mostrarFormularioDeAltaMantenimiento(@PathVariable("matricula") String matricula, Model model) {
    	model.addAttribute("mantenimiento", new Mantenimiento());
    	model.addAttribute(matricula);
    	model.addAttribute("categorias", categoriaService.listarCategorias());

    	return "mantenimiento/agregarMantenimiento";
    }
    
    @PostMapping("/mantenimientos/{matricula}")
    public String crearMantenimiento(MantenimientoEntity mantenimiento, @RequestParam("matricula") String matricula) {
    	Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);
    	if (cocheOpt.isPresent()) {
    		mantenimiento.setCoche(cocheOpt.get());
    	}
    	mantenimientoService.guardarMantenimiento(mantenimiento);
    	return "redirect:/coches/" + matricula;
    }
    
    @GetMapping("/eliminarMantenimiento/{id}")
    public String eliminarMantenimiento(@PathVariable("id") Integer id) {
    	mantenimientoService.eliminarMantenimiento(id);
    	return "redirect:/coches";
    }
    
    @GetMapping("/mantenimientos/buscador/{matricula}")
    public String mostrarFormularioBuscadorMantenimientos(@PathVariable("matricula") String matricula, Model model) {
    	List<CategoriaEntity> categorias = categoriaService.listarCategorias().stream()
    	        .filter(categoriaEntity -> categoriaEntity.getTipoCategoria().equals(1))
    	        .collect(Collectors.toList());
    	model.addAttribute("mantenimiento", new MantenimientoEntity());
    	model.addAttribute("coche", cocheService.obtenerPorId(matricula));
    	model.addAttribute("categorias", categorias);
    	model.addAttribute(matricula);
    	return "mantenimiento/buscadorMantenimientos";
    }
    
    @GetMapping("/mantenimientos/buscador")
    public String buscarMantenimientos(@RequestParam(required = false) String matricula, 
                                       @RequestParam(required = false) Long idCategoria,
                                       @RequestParam(required = false) Double precioDesde,
                                       @RequestParam(required = false) Double precioHasta,
                                       @RequestParam(required = false) Integer ano,
                                       Model model) {
    	List<CategoriaEntity> categorias = categoriaService.listarCategorias().stream()
    	        .filter(categoriaEntity -> categoriaEntity.getTipoCategoria().equals(1))
    	        .collect(Collectors.toList());
        Object mantenimientos = mantenimientoService.buscarMantenimientosConFiltros(matricula, idCategoria, precioDesde, precioHasta, ano);
        model.addAttribute("categorias", categorias);
        model.addAttribute("matricula", matricula);
        model.addAttribute("resultado", mantenimientos);
        return "mantenimiento/buscadorMantenimientos";
    }
    
    @GetMapping("/visualizarMantenimiento/{idMantenimiento}")
    public String buscarMantenimientoPorId(@PathVariable("idMantenimiento") Integer idMantenimiento, Model model) {
    	 Optional<MantenimientoEntity> mantenimiento = mantenimientoService.obtenerPorId(idMantenimiento);
         if (mantenimiento.isPresent()) {
        	 String matricula = mantenimiento.get().getCoche().getMatricula();
             model.addAttribute("mantenimiento", mantenimiento.get());
             model.addAttribute("matricula", matricula);
         }
    	return "mantenimiento/visualizarMantenimiento";
    }
    
    @GetMapping("/coches/{matricula}/modal")
    public ResponseEntity<Map<String, String>> mostrarMensajeModal(@PathVariable("matricula") String matricula) {
        List<MantenimientoEntity> mantenimientos = mantenimientoService.listarMantenimientos(matricula);
        int cambioKmMotor, cambioKmCambio, diferenciaKmMotor, diferenciaKmCambio = 0;
        List<MantenimientoEntity> listaMotor = new ArrayList<>();
        List<MantenimientoEntity> listaCambio = new ArrayList<>();
        String mensajeMotor="";String mensajeCambio = "";
        /* Mensaje para warning mantenimiento*/
        for (MantenimientoEntity mantenimiento : mantenimientos) {
            if (Long.valueOf(2).equals(mantenimiento.getCategoria().getIdCategoria())) {
            	listaMotor.add(mantenimiento);
            	listaMotor.sort(Comparator.comparing(MantenimientoEntity::getFecha).reversed());
            	if(!listaMotor.isEmpty()) {
	                cambioKmMotor = listaMotor.get(0).getKmMantenimiento();
	                diferenciaKmMotor = (cambioKmMotor + 20000) - mantenimiento.getCoche().getKilometros();
	                mensajeMotor = "Quedan <strong>" + diferenciaKmMotor + " km</strong> para cambiar el <strong>aceite del motor.</strong>";
            	}
            } else if (Long.valueOf(3).equals(mantenimiento.getCategoria().getIdCategoria())) {
            	listaCambio.add(mantenimiento);
            	listaCambio.sort(Comparator.comparing(MantenimientoEntity::getFecha).reversed());
            	if(!listaCambio.isEmpty()){
	            	cambioKmCambio = listaCambio.get(0).getKmMantenimiento();
	                diferenciaKmCambio = (cambioKmCambio + 60000) - mantenimiento.getCoche().getKilometros();
	                mensajeCambio = "Quedan <strong>" + diferenciaKmCambio + " km</strong> para cambiar el <strong>aceite del cambio.</strong>";
            	}
            }
        }
        
        /** Mensaje para warningItv**/
        List<ItvEntity> listaItvs = itvService.listarItvs(matricula);
        List<Itv> listaItvsModel = new ArrayList<>();
        for (ItvEntity itvEntity : listaItvs) {
        	Itv itv = TransformadorItv.itvEntityToItv(itvEntity);
        	listaItvsModel.add(itv);
        }
        listaItvsModel.sort(Comparator.comparing(Itv::getKmRevision).reversed());

        Map<String, String> response = new HashMap<>();
        response.put("mensajeModalCambio", mensajeCambio);
        response.put("mensajeModalMotor", mensajeMotor);
        if(!listaItvsModel.isEmpty()) {
        	Itv itv= listaItvsModel.get(0);
        	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        	String fechaFormateada = formatter.format(itv.getFechaProximaItv());
        	String mensajeItv = "La fecha de proxima ITV es: <strong>" + fechaFormateada + "</strong>";
        	response.put("mensajeModalItv", mensajeItv);
        }
        return ResponseEntity.ok(response);
    }
    
    /****************************************/
    /***************   ITV   ****************/
    /****************************************/
    
    @GetMapping("/itvs/lista/{matricula}")
    public String listarItvs(@PathVariable("matricula") String matricula, Model model) {
    	List<ItvEntity> listaItvEntity = itvService.listarItvs(matricula);
    	List<Itv> listaItv = new ArrayList<>();
    	for (ItvEntity itvEntity : listaItvEntity) {
        	Itv itv = TransformadorItv.itvEntityToItv(itvEntity);
        	listaItv.add(itv);
        }
    	listaItv.sort(Comparator.comparing(Itv::getFechaApto).reversed());
    	model.addAttribute("listaItv", listaItv);
    	model.addAttribute(matricula);
    	return "itv/listaItv";
    }
    
    @GetMapping("/itvs/{matricula}")
    public String mostrarFormularioDeAltaItv(@PathVariable("matricula") String matricula, Model model) {
    	model.addAttribute("itv", new Itv());
    	model.addAttribute(matricula);
    	return "itv/agregarItv";
    }
    
    @PostMapping("/itvs/{matricula}")
    public String crearItv(ItvEntity itv, @RequestParam("matricula") String matricula) {
    	Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);
    	if (cocheOpt.isPresent()) {
    		itv.setCoche(cocheOpt.get());
    	}
    	itvService.guardarItv(itv);
    	return "redirect:/coches/" + matricula;
    }
    
    @GetMapping("/eliminarItv/{id}")
    public String eliminarItv(@PathVariable("id") Integer id) {
    	itvService.eliminarItv(id);
    	return "redirect:/coches";
    }
    
    @GetMapping("/visualizarItv/{idFactura}")
    public String buscarItvPorId(@PathVariable("idFactura") Integer idFactura, Model model) {
    	 Optional<ItvEntity> itv = itvService.obtenerPorId(idFactura);
         if (itv.isPresent()) {
        	 String matricula = itv.get().getCoche().getMatricula();
             model.addAttribute("itv", itv.get());
             model.addAttribute("matricula", matricula);
         }
    	return "itv/visualizarItv";
    }
    
    /****************************************/
    /************   RECAMBIOS   *************/
    /****************************************/
    
    @GetMapping("/recambios")
    public String mostrarFormularioDeAltaRecambio( Model model) {
    	List<CategoriaEntity> categoriasRecambio = new ArrayList<>();
    	List<CategoriaEntity> categorias = categoriaService.listarCategorias();
    	for (CategoriaEntity categoriaEntity : categorias) {
			if(categoriaEntity.getTipoCategoria().equals(2)) {
				categoriasRecambio.add(categoriaEntity);
			}
		}
    	model.addAttribute("recambio", new Recambio());
    	model.addAttribute("categorias", categoriasRecambio);

    	return "recambio/agregarRecambio";
    }
    
    @PostMapping("/recambios")
    public String crearRecambio(RecambioEntity recambio) {
    	recambioService.guardarRecambio(recambio);
    	return "redirect:/coches";
    }
    
}
