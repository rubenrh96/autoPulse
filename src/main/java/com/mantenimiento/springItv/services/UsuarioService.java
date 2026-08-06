package com.mantenimiento.springItv.services;

import com.mantenimiento.springItv.entities.UsuarioEntity;
import com.mantenimiento.springItv.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioEntity registrar(UsuarioEntity usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public UsuarioEntity login(String username, String rawPassword) {
        UsuarioEntity u = usuarioRepository.findByUsername(username);
        if (u != null && passwordEncoder.matches(rawPassword, u.getPassword())) {
            return u;
        }
        return null;
    }
}
