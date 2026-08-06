package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CocheDto {

    @NotBlank(message = "La matrícula es obligatoria")
    private String matricula;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    private String color;

    @Min(value = 0, message = "Los CV no pueden ser negativos")
    private int cv;

    @Min(value = 1900, message = "El año debe ser mayor a 1900")
    private int ano;

    @Min(value = 0, message = "Los kilómetros no pueden ser negativos")
    private int kilometros;
}



