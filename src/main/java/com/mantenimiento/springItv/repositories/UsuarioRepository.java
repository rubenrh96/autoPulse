package com.mantenimiento.springItv.repositories;

import com.mantenimiento.springItv.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    UsuarioEntity findByUsername(String username);
}

