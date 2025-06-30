package com.mantenimiento.springItv.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class GastoMensualDto {

    private String matricula;
    private String mes;
    private double total;
}
