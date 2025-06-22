package com.mantenimiento.springItv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mantenimiento.springItv.entities.CocheEntity;

import java.util.List;

public interface CocheRepository extends JpaRepository<CocheEntity, String> {

    List<CocheEntity> findByUsuarioId(Long usuarioId);


}
