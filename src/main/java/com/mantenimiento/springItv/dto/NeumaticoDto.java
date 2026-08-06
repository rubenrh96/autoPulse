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
public class NeumaticoDto {

    private Integer idNeumatico;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @Min(value = 0, message = "El ancho de llanta no puede ser negativo")
    private int anchoLlanta;

    @Min(value = 0, message = "El perfil de llanta no puede ser negativo")
    private int perfilLlanta;

    private String diametroLlanta;

    @Min(value = 0, message = "El índice de carga no puede ser negativo")
    private int indiceCarga;

    private String indiceVelocidad;

    @DecimalMin(value = "0", message = "El precio no puede ser negativo")
    private double precio;

    @Min(value = 0, message = "Los kilómetros de montaje no pueden ser negativos")
    private int kmMontaje;

    @NotBlank(message = "La fecha de montaje es obligatoria")
    private String fechaMontaje; // yyyy-MM-dd

    private String descripcion;
    private boolean ms;
    private int numero;
    private String matricula;
}



