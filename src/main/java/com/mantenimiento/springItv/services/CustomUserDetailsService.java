package com.mantenimiento.springItv.services;

import com.mantenimiento.springItv.entities.UsuarioEntity;
import com.mantenimiento.springItv.repositories.UsuarioRepository;
import com.mantenimiento.springItv.secutity.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        return new CustomUserDetails(usuario);
    }
}
