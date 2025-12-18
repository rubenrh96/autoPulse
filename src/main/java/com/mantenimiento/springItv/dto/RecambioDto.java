package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecambioDto {

    private Integer idRecambio;
    private String descripcion;
    private BigDecimal precio;
    private String fechaCompra; // yyyy-MM-dd
    private String cantidad;
    private Long idCategoria;
    private String categoria;
}



