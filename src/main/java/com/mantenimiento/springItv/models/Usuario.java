package com.mantenimiento.springItv.models;

import lombok.Data;

@Data
public class Usuario {
    private String username;
    private String password;
    private String email;
    private String nombre;
    private String apellidos;
}
