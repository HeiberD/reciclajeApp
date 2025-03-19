package com.proyecto.reciclaje.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroUsuarioDTO {
    private String username;
    private String password;
    private String role; // ADMIN, USUARIO, EMPRESA_RECOLECTORA
}