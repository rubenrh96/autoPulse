package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItvDto {

    private Integer idFactura;
    private double precio;
    private boolean apto;
    private String fechaApto;       // formato yyyy-MM-dd
    private int kmRevision;
    private String fechaProximaItv; // formato yyyy-MM-dd
    private String observaciones;
    private String matricula;
}


