package com.mantenimiento.springItv.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mantenimiento.springItv.services.RepostajeService;
import com.mantenimiento.springItv.services.MantenimientoService;
import com.mantenimiento.springItv.services.NeumaticoService;
import com.mantenimiento.springItv.services.ItvService;
import com.mantenimiento.springItv.entities.RepostajeEntity;
import com.mantenimiento.springItv.entities.MantenimientoEntity;
import com.mantenimiento.springItv.entities.NeumaticoEntity;
import com.mantenimiento.springItv.entities.ItvEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GraficoController {

    @Autowired
    private RepostajeService repostajeService;

    @Autowired
    private MantenimientoService mantenimientoService;

    @Autowired
    private NeumaticoService neumaticoService;

    @Autowired
    private ItvService itvService;

//    @GetMapping("/graficos")
//    public String verGraficos(Model model) throws JsonProcessingException {
//        ObjectMapper mapper = new ObjectMapper();
//
//        List<RepostajeEntity> repostajes = repostajeService.listarTodos();
//        List<MantenimientoEntity> mantenimientos = mantenimientoService.listarTodos();
//        List<NeumaticoEntity> neumaticos = neumaticoService.listarTodos();
//        List<ItvEntity> itvs = itvService.listarTodos();
//
//        model.addAttribute("repostajesJson", mapper.writeValueAsString(repostajes));
//        model.addAttribute("mantenimientosJson", mapper.writeValueAsString(mantenimientos));
//        model.addAttribute("neumaticosJson", mapper.writeValueAsString(neumaticos));
//        model.addAttribute("itvsJson", mapper.writeValueAsString(itvs));
//
//        return "grafico/graficos";
//    }

    @GetMapping("/graficos")
    public String verGraficos(Model model) {
        return "grafico/graficos";
    }
}
