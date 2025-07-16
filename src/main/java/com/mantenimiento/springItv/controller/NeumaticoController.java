package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.entities.NeumaticoEntity;
import com.mantenimiento.springItv.models.Neumatico;
import com.mantenimiento.springItv.models.Repostaje;
import com.mantenimiento.springItv.services.CocheService;
import com.mantenimiento.springItv.services.NeumaticoService;
import com.mantenimiento.springItv.transformadores.TransformadorNeumatico;
import com.mantenimiento.springItv.transformadores.TransformadorRepostaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/neumaticos")
public class NeumaticoController {

    @Autowired
    private NeumaticoService neumaticoService;

    @Autowired
    private CocheService cocheService;

    @GetMapping("/{matricula}")
    public String mostrarFormularioDeAlta(@PathVariable("matricula") String matricula, Model model) {
        model.addAttribute("neumatico", new Neumatico());
        model.addAttribute("matricula", matricula);
        return "neumatico/agregarNeumatico";
    }

    @PostMapping("/{matricula}")
    public String crearNeumatico(NeumaticoEntity neumatico, @RequestParam("matricula") String matricula) {
        cocheService.obtenerPorId(matricula).ifPresent(neumatico::setCoche);
        neumaticoService.guardarNeumatico(neumatico);
        return "redirect:/coches/" + matricula + "?success=neumatico";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarNeumatico(@PathVariable("id") Integer id) {
        neumaticoService.eliminarNeumatico(id);
        return "redirect:/coches";
    }

    @GetMapping("/lista/{matricula}")
    public String listarNeumaticos(@PathVariable("matricula") String matricula, Model model) {
        List<Neumatico> lista = neumaticoService.listarNeumaticos(matricula).stream()
                .map(TransformadorNeumatico::neumaticoEntityToNeumatico)
                .sorted(Comparator.comparing(Neumatico::getFechaMontaje).reversed())
                .collect(Collectors.toList());
        model.addAttribute("listaNeumaticos", lista);
        model.addAttribute("matricula", matricula);
        return "neumatico/listaNeumatico";
    }
}