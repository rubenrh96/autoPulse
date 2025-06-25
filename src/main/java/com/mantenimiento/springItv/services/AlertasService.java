// src/main/java/com/mantenimiento/springItv/services/AlertaService.java
package com.mantenimiento.springItv.services;

import com.mantenimiento.springItv.dto.AlertaDto;
import com.mantenimiento.springItv.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AlertasService {

    private final CocheService cocheService;
    private final ItvService itvService;
    private final MantenimientoService mantenimientoService;
    public List<AlertaDto> generarAlertasUsuario(Long usuarioId) {

        List<AlertaDto> alertas = new ArrayList<>();

        /* 1) ITV < 1 mes */
        List<CocheEntity> coches = cocheService.listarCochesPorUsuario(usuarioId);
        for (CocheEntity coche : coches) {

            // —— ITV ——————————————————————————
            List<ItvEntity> itvs = itvService.listarItvs(coche.getMatricula());
            itvs.sort(Comparator.comparing(ItvEntity::getFechaProximaItv).reversed());

            if (!itvs.isEmpty()) {
                ItvEntity itv = itvs.get(0);
                LocalDate fechaProxima = itv.getFechaProximaItv();
                if (!fechaProxima.isAfter(LocalDate.now().plusMonths(1))) {
                    String fechaStr = new SimpleDateFormat("dd-MM-yyyy")
                            .format(java.sql.Date.valueOf(fechaProxima));
                    alertas.add(new AlertaDto(
                            "warning",
                            "El coche con matrícula <strong>" + coche.getMatricula() +
                                    "</strong> debe pasar la ITV el <strong>" + fechaStr +
                                    "</strong> (queda menos de 1 mes)."
                    ));
                }
            }

            // —— MANTENIMIENTOS (motor y cambio) ————————————
            List<MantenimientoEntity> mants = mantenimientoService.listarMantenimientos(coche.getMatricula());

            // separa por categoría:
            mants.stream()
                    .filter(m -> m.getCategoria().getIdCategoria() == 2L) // Motor
                    .max(Comparator.comparing(MantenimientoEntity::getFecha))
                    .ifPresent(ultimoMotor -> {
                        int restante = (ultimoMotor.getKmMantenimiento() + 20_000)
                                - coche.getKilometros();
                        if (restante <= 2_000) {
                            alertas.add(new AlertaDto(
                                    "danger",
                                    "Al coche <strong>" + coche.getMatricula() +
                                            "</strong> le quedan <strong>" + restante +
                                            " km</strong> para el cambio de <strong>aceite de motor</strong>."
                            ));
                        }
                    });

            mants.stream()
                    .filter(m -> m.getCategoria().getIdCategoria() == 3L) // Cambio
                    .max(Comparator.comparing(MantenimientoEntity::getFecha))
                    .ifPresent(ultimoCambio -> {
                        int restante = (ultimoCambio.getKmMantenimiento() + 60_000)
                                - coche.getKilometros();
                        if (restante <= 2_000) {
                            alertas.add(new AlertaDto(
                                    "danger",
                                    "Al coche <strong>" + coche.getMatricula() +
                                            "</strong> le quedan <strong>" + restante +
                                            " km</strong> para el cambio de <strong>aceite de la caja</strong>."
                            ));
                        }
                    });
        }
        return alertas;
    }
}
