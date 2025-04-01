package com.mantenimiento.springItv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mantenimiento.springItv.entities.MantenimientoEntity;

public interface MantenimientoRepository extends JpaRepository<MantenimientoEntity, Integer>, JpaSpecificationExecutor<MantenimientoEntity> {

}
