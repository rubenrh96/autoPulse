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
public class RepostajeDto {

    private Integer idRepostaje;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private double precio;

    @DecimalMin(value = "0.01", message = "Los litros deben ser mayores a 0")
    private double litros;

    @DecimalMin(value = "0", message = "El precio por litro no puede ser negativo")
    private double precioLitro;

    @Min(value = 0, message = "Los kilómetros no pueden ser negativos")
    private int kmRepostaje;

    @NotBlank(message = "La fecha es obligatoria")
    private String fecha;   // yyyy-MM-dd

    private String matricula;
}



