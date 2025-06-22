package com.mantenimiento.springItv.services;

import com.mantenimiento.springItv.entities.UsuarioEntity;
import com.mantenimiento.springItv.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioEntity registrar(UsuarioEntity usuario) {
        return usuarioRepository.save(usuario);
    }

    public UsuarioEntity login(String username, String rawPassword) {
        UsuarioEntity u = usuarioRepository.findByUsername(username);
        if (u != null && rawPassword.equals(u.getPassword())) {
            return u;
        }
        return null;
    }
}
