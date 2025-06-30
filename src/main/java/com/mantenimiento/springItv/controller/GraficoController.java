package com.mantenimiento.springItv.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mantenimiento.springItv.dto.GastoKmPorMesDto;
import com.mantenimiento.springItv.dto.GastoPorCocheDto;
import com.mantenimiento.springItv.services.RepostajeService;
import com.mantenimiento.springItv.services.MantenimientoService;
import com.mantenimiento.springItv.services.NeumaticoService;
import com.mantenimiento.springItv.services.ItvService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/graficos")
public class GraficoController {

    @Autowired
    private RepostajeService repostajeService;

    @Autowired
    private MantenimientoService mantenimientoService;

    @Autowired
    private NeumaticoService neumaticoService;

    @Autowired
    private ItvService itvService;

    private final ObjectMapper mapper;

    @GetMapping
    public String graficoGastosPorCoche(Model model, Principal principal) throws JsonProcessingException {

        var lista = repostajeService.gastoTotalPorCocheDe(principal.getName());
        List<String> etiquetas = lista.stream()
                .map(GastoPorCocheDto::getMatricula)
                .toList();
        List<Double> datos = lista.stream()
                .map(GastoPorCocheDto::getTotal)
                .toList();
        model.addAttribute("labelsJson", new ObjectMapper().writeValueAsString(lista.stream().map(GastoPorCocheDto::getMatricula).toList()));
        model.addAttribute("dataJson", new ObjectMapper().writeValueAsString(lista.stream().map(GastoPorCocheDto::getTotal).toList()));

        var graf = mantenimientoService.costePorCategoria(principal.getName());

        model.addAttribute("labelsMantenimientoJson",
                mapper.writeValueAsString(graf.get("labels")));
        model.addAttribute("dataMantenimientoJson",
                mapper.writeValueAsString(graf.get("data")));

        return "grafico/graficos";
    }
}

