package com.mantenimiento.springItv.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mantenimiento.springItv.entities.CategoriaEntity;
import com.mantenimiento.springItv.entities.MantenimientoEntity;
import com.mantenimiento.springItv.models.Mantenimiento;
import com.mantenimiento.springItv.services.CategoriaService;
import com.mantenimiento.springItv.services.CocheService;
import com.mantenimiento.springItv.services.MantenimientoService;
import com.mantenimiento.springItv.transformadores.TransformadorMantenimiento;

@Controller
public class MantenimientoController {

    @Autowired
    private MantenimientoService mantenimientoService;

    @Autowired
    private CocheService cocheService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/mantenimientos/lista/{matricula}")
    public String listarMantenimientos(@PathVariable("matricula") String matricula, Model model) {
        List<MantenimientoEntity> listaMantenimientoEntity = mantenimientoService.listarMantenimientos(matricula);
        List<Mantenimiento> listaMantenimiento = listaMantenimientoEntity.stream()
                .map(TransformadorMantenimiento::mantenimientoEntityToMantenimiento)
                .sorted(Comparator.comparing(Mantenimiento::getFecha).reversed())
                .collect(Collectors.toList());
        model.addAttribute("listaMantenimiento", listaMantenimiento);
        model.addAttribute("matricula", matricula);
        return "mantenimiento/listaMantenimiento";
    }

    @GetMapping("/mantenimientos/{matricula}")
    public String mostrarFormularioDeAltaMantenimiento(@PathVariable("matricula") String matricula, Model model) {
        model.addAttribute("mantenimiento", new Mantenimiento());
        model.addAttribute("matricula", matricula);
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "mantenimiento/agregarMantenimiento";
    }

    @PostMapping("/mantenimientos/{matricula}")
    public String crearMantenimiento(MantenimientoEntity mantenimiento, @RequestParam("matricula") String matricula) {
        cocheService.obtenerPorId(matricula).ifPresent(mantenimiento::setCoche);
        mantenimientoService.guardarMantenimiento(mantenimiento);
        return "redirect:/coches/" + matricula + "?success=mantenimiento";
    }

    @GetMapping("/eliminarMantenimiento/{id}")
    public String eliminarMantenimiento(@PathVariable("id") Integer id) {
        mantenimientoService.eliminarMantenimiento(id);
        return "redirect:/coches";
    }

    @GetMapping("/mantenimientos/buscador/{matricula}")
    public String mostrarFormularioBuscadorMantenimientos(@PathVariable("matricula") String matricula, Model model) {
        List<CategoriaEntity> categorias = categoriaService.listarCategorias().stream()
                .filter(cat -> cat.getTipoCategoria().equals(1))
                .collect(Collectors.toList());
        model.addAttribute("mantenimiento", new MantenimientoEntity());
        model.addAttribute("coche", cocheService.obtenerPorId(matricula));
        model.addAttribute("categorias", categorias);
        model.addAttribute("matricula", matricula);
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
                .filter(cat -> cat.getTipoCategoria().equals(1))
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
        mantenimiento.ifPresent(m -> {
            model.addAttribute("mantenimiento", m);
            model.addAttribute("matricula", m.getCoche().getMatricula());
        });
        return "mantenimiento/visualizarMantenimiento";
    }
}
