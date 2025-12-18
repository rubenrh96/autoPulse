package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.dto.GastoMensualDto;
import com.mantenimiento.springItv.dto.GastoPorCocheDto;
import com.mantenimiento.springItv.services.MantenimientoService;
import com.mantenimiento.springItv.services.RepostajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/graficos")
@RequiredArgsConstructor
public class GraficoRestController {

    private final RepostajeService repostajeService;
    private final MantenimientoService mantenimientoService;

    @GetMapping("/gasto-por-coche")
    public List<GastoPorCocheDto> gastoPorCoche(Principal principal) {
        return repostajeService.gastoTotalPorCocheDe(principal.getName());
    }

    @GetMapping("/coste-por-categoria")
    public Map<String, Object> costePorCategoria(Principal principal) {
        return mantenimientoService.costePorCategoria(principal.getName());
    }

    @GetMapping("/gasto-mensual")
    public Map<String, Object> gastoMensual(Principal principal) {
        List<GastoMensualDto> listaMensual = repostajeService.gastoMensualPorCocheDe(principal.getName());
        Map<String, Double> gastoPorMes = new TreeMap<>();
        for (GastoMensualDto dto : listaMensual) {
            gastoPorMes.merge(dto.getMes(), dto.getTotal(), Double::sum);
        }
        var labelsMes = new ArrayList<>(gastoPorMes.keySet());
        var dataMes   = new ArrayList<>(gastoPorMes.values());

        return Map.of(
                "labels", labelsMes,
                "data", dataMes
        );
    }
}



