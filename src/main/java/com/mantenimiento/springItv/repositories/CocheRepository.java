package com.mantenimiento.springItv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mantenimiento.springItv.entities.CocheEntity;

public interface CocheRepository extends JpaRepository<CocheEntity, String> {

}
