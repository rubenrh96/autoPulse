package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.dto.GastoPorCocheDto;
import com.mantenimiento.springItv.services.GraficoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/graficos")
@RequiredArgsConstructor
public class GraficoRestController {

    private final GraficoService graficoService;

    @GetMapping("/gasto-por-coche")
    public List<GastoPorCocheDto> gastoPorCoche(
            Principal principal,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return graficoService.gastoPorCoche(principal.getName(), desde, hasta);
    }

    @GetMapping("/coste-por-tipo")
    public Map<String, Object> costePorTipo(
            Principal principal,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return graficoService.costePorTipo(principal.getName(), desde, hasta);
    }

    @GetMapping("/gasto-mensual")
    public Map<String, Object> gastoMensual(
            Principal principal,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return graficoService.gastoMensual(principal.getName(), desde, hasta);
    }

    @GetMapping("/gasto-total")
    public Map<String, Double> gastoTotal(
            Principal principal,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return Map.of("total", graficoService.gastoTotal(principal.getName(), desde, hasta));
    }
}
