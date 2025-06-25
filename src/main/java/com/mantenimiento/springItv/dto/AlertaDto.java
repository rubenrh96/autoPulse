// src/main/java/com/mantenimiento/springItv/dto/AlertaDto.java
package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlertaDto {
    private final String tipo;
    private final String mensaje;
}
