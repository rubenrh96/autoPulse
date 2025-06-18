package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.entities.NeumaticoEntity;
import com.mantenimiento.springItv.models.Neumatico;
import com.mantenimiento.springItv.services.CocheService;
import com.mantenimiento.springItv.services.NeumaticoService;
import com.mantenimiento.springItv.transformadores.TransformadorNeumatico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        return "redirect:/coches/" + matricula;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarNeumatico(@PathVariable("id") Integer id) {
        neumaticoService.eliminarNeumatico(id);
        return "redirect:/coches";
    }
}