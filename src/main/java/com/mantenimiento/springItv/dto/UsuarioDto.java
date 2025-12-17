package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioDto {

    private final Long id;
    private final String username;
    private final String nombre;
    private final String apellidos;
    private final String email;
}


