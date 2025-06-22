package com.mantenimiento.springItv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mantenimiento.springItv.entities.RecambioEntity;

import java.util.List;

public interface RecambioRepository extends JpaRepository<RecambioEntity, Integer>{

    List<RecambioEntity> findByUsuarioId(Long usuarioId);

}
