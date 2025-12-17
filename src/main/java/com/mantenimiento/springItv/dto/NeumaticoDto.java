package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NeumaticoDto {

    private Integer idNeumatico;
    private String marca;
    private String modelo;
    private int anchoLlanta;
    private int perfilLlanta;
    private String diametroLlanta;
    private int indiceCarga;
    private String indiceVelocidad;
    private double precio;
    private int kmMontaje;
    private String fechaMontaje; // yyyy-MM-dd
    private String descripcion;
    private boolean ms;
    private int numero;
    private String matricula;
}


