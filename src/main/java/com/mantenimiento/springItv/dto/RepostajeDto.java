package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepostajeDto {

    private Integer idRepostaje;
    private double precio;
    private double litros;
    private double precioLitro;
    private int kmRepostaje;
    private String fecha;   // yyyy-MM-dd
    private String matricula;
}



