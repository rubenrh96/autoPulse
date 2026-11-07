package com.mantenimiento.springItv.services;

import com.mantenimiento.springItv.dto.AlertaDto;
import com.mantenimiento.springItv.dto.AlertaVehiculoDto;
import com.mantenimiento.springItv.entities.CocheEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertasService {

    private final CocheService cocheService;
    private final ItvService itvService;
    private final SeguroService seguroService;
    private final MantenimientoService mantenimientoService;

    /** Alertas estructuradas de todos los coches del usuario (API móvil). */
    public List<AlertaVehiculoDto> alertasDeUsuario(Long usuarioId, boolean soloUrgentes) {
        List<AlertaVehiculoDto> res = new ArrayList<>();
        for (CocheEntity coche : cocheService.listarCochesPorUsuario(usuarioId)) {
            res.addAll(alertasDeCoche(coche, soloUrgentes));
        }
        return res;
    }

    public List<AlertaVehiculoDto> alertasDeCoche(CocheEntity coche, boolean soloUrgentes) {
        String mat = coche.getMatricula();
        List<AlertaVehiculoDto> todas = AlertasCalculator.calcular(
                coche,
                itvService.listarItvs(mat),
                seguroService.listarSeguros(mat),
                mantenimientoService.listarMantenimientos(mat),
                LocalDate.now());
        if (!soloUrgentes) return todas;
        return todas.stream().filter(a -> !AlertasCalculator.OK.equals(a.getUrgencia())).toList();
    }

    /** Versión con HTML para la web Thymeleaf (banner de alertas); misma lógica que la API. */
    public List<AlertaDto> generarAlertasUsuario(Long usuarioId) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<AlertaDto> alertas = new ArrayList<>();
        for (AlertaVehiculoDto a : alertasDeUsuario(usuarioId, true)) {
            String m = "<strong>" + a.getMatricula() + "</strong>";
            String texto;
            if (a.getFechaObjetivo() != null) {
                String f = LocalDate.parse(a.getFechaObjetivo()).format(fmt);
                boolean vencida = a.getDiasRestantes() < 0;
                if ("ITV".equals(a.getTipo())) {
                    texto = "El coche " + m + (vencida ? " tenía la ITV el " : " debe pasar la ITV el ")
                            + "<strong>" + f + "</strong>" + (vencida ? " (vencida)." : ".");
                } else {
                    texto = "El seguro del coche " + m + (vencida ? " venció el " : " se renueva el ")
                            + "<strong>" + f + "</strong>.";
                }
            } else {
                String que = "ACEITE_CAJA".equals(a.getTipo()) ? "aceite de la caja" : "aceite de motor";
                texto = a.getKmRestantes() < 0
                        ? "El coche " + m + " ya pasó <strong>" + Math.abs(a.getKmRestantes())
                                + " km</strong> del cambio de <strong>" + que + "</strong>."
                        : "Al coche " + m + " le quedan <strong>" + a.getKmRestantes()
                                + " km</strong> para el cambio de <strong>" + que + "</strong>.";
            }
            alertas.add(new AlertaDto(AlertasCalculator.DANGER.equals(a.getUrgencia()) ? "danger" : "warning", texto));
        }
        return alertas;
    }
}
