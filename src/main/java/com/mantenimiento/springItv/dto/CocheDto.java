package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CocheDto {

    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    private int cv;
    private int ano;
    private int kilometros;
}



