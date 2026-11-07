package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Alerta/tarea próxima de un vehículo, calculada en backend (única fuente de verdad para web y móvil).
 * Los campos que no aplican al tipo van a null: las de fecha (ITV, SEGURO) rellenan fechaObjetivo y
 * diasRestantes; las de aceite rellenan kmObjetivo, kmRestantes, ultimoKm y ultimaFecha.
 */
@Getter
@Builder
@AllArgsConstructor
public class AlertaVehiculoDto {
    private final String id;
    private final String matricula;
    /** ITV | SEGURO | ACEITE_MOTOR | ACEITE_CAJA */
    private final String tipo;
    /** ok | warning | danger */
    private final String urgencia;
    private final String titulo;
    private final String fechaObjetivo;   // yyyy-MM-dd
    private final Integer diasRestantes;  // negativo = vencida
    private final Integer kmObjetivo;
    private final Integer kmRestantes;    // negativo = pasado
    private final Integer ultimoKm;
    private final String ultimaFecha;     // yyyy-MM-dd
}
