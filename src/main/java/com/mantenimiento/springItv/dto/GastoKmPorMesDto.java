package com.mantenimiento.springItv.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class GastoKmPorMesDto {
    private String matricula;
    private String mes;
    private double totalGasto;
    private int totalKm;

    public GastoKmPorMesDto(String matricula,
                            Integer anio,
                            Integer mes,
                            Double totalGasto,
                            Long   totalKm) {
        this(matricula,
                anio + "-" + String.format("%02d", mes),
                totalGasto,
                totalKm.intValue());
    }
}
