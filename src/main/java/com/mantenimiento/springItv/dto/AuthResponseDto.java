package com.mantenimiento.springItv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDto {

    private final String accessToken;
    private final String tokenType;
    private final UsuarioDto usuario;
}


