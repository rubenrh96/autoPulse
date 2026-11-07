package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeguroDto {

    private Integer idSeguro;

    @DecimalMin(value = "0", message = "El precio no puede ser negativo")
    private double precio;

    @NotBlank(message = "La compañía es obligatoria")
    private String compania;

    @NotBlank(message = "La fecha de inicio es obligatoria")
    private String fechaInicio;      // formato yyyy-MM-dd

    @NotBlank(message = "La fecha de renovación es obligatoria")
    private String fechaRenovacion;  // formato yyyy-MM-dd

    private String observaciones;
    private String matricula;
}
