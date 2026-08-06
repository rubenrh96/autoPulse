package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItvDto {

    private Integer idFactura;

    @DecimalMin(value = "0", message = "El precio no puede ser negativo")
    private double precio;

    private boolean apto;

    @NotBlank(message = "La fecha de apto es obligatoria")
    private String fechaApto;       // formato yyyy-MM-dd

    @Min(value = 0, message = "Los kilómetros no pueden ser negativos")
    private int kmRevision;

    private String fechaProximaItv; // formato yyyy-MM-dd
    private String observaciones;
    private String matricula;
}



