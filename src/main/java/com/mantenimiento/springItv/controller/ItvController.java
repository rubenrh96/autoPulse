package com.mantenimiento.springItv.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.entities.ItvEntity;
import com.mantenimiento.springItv.models.Itv;
import com.mantenimiento.springItv.services.CocheService;
import com.mantenimiento.springItv.services.ItvService;
import com.mantenimiento.springItv.transformadores.TransformadorItv;

@Controller
public class ItvController {

	@Autowired
	private ItvService itvService;

	@Autowired
	private CocheService cocheService;

	@GetMapping("/itvs/lista/{matricula}")
	public String listarItvs(@PathVariable("matricula") String matricula, Model model) {
		List<ItvEntity> listaItvEntity = itvService.listarItvs(matricula);
		List<Itv> listaItv = new ArrayList<>();
		for (ItvEntity itvEntity : listaItvEntity) {
			listaItv.add(TransformadorItv.itvEntityToItv(itvEntity));
		}
		listaItv.sort(Comparator.comparing(Itv::getFechaApto).reversed());
		model.addAttribute("listaItv", listaItv);
		model.addAttribute("matricula", matricula);
		return "itv/listaItv";
	}

	@GetMapping("/itvs/{matricula}")
	public String mostrarFormularioDeAltaItv(@PathVariable("matricula") String matricula, Model model) {
		model.addAttribute("itv", new Itv());
		model.addAttribute("matricula", matricula);
		return "itv/agregarItv";
	}

	@PostMapping("/itvs/{matricula}")
	public String crearItv(ItvEntity itv, @RequestParam("matricula") String matricula) {
		Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);
		cocheOpt.ifPresent(itv::setCoche);
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
		itv.ifPresent(i -> {
			model.addAttribute("itv", i);
			model.addAttribute("matricula", i.getCoche().getMatricula());
		});
		return "itv/visualizarItv";
	}

	@GetMapping("/coches/{matricula}/modal/itv")
	public ResponseEntity<String> mensajeModalItv(@PathVariable("matricula") String matricula) {
		List<ItvEntity> listaItvs = itvService.listarItvs(matricula);
		List<Itv> listaItvsModel = new ArrayList<>();
		for (ItvEntity itvEntity : listaItvs) {
			listaItvsModel.add(TransformadorItv.itvEntityToItv(itvEntity));
		}
		listaItvsModel.sort(Comparator.comparing(Itv::getKmRevision).reversed());

		if (!listaItvsModel.isEmpty()) {
			Itv itv = listaItvsModel.get(0);
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			String fechaFormateada = formatter.format(itv.getFechaProximaItv());
			String mensajeItv = "La fecha de proxima ITV es: <strong>" + fechaFormateada + "</strong>";
			return ResponseEntity.ok(mensajeItv);
		}

		return ResponseEntity.ok("");
	}
}