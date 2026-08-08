package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

// Línea de gasto homogénea para agregar ITV, Mantenimiento, Repostaje, Neumático y Recambio
// bajo un mismo tipo antes de calcular gasto-por-coche / coste-por-tipo / gasto-mensual.
@Getter
@AllArgsConstructor
public class GastoLineaDto {
    private String matricula; // null para Recambio, que no está asociado a un coche
    private Date fecha;
    private Double precio;
    private String tipo;
}
