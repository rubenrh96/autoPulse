package com.mantenimiento.springItv.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequestDto {

    private String username;
    private String nombre;
    private String apellidos;
    private String email;
    private String password;
}


