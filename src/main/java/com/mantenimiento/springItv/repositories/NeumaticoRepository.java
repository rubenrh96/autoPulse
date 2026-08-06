package com.mantenimiento.springItv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mantenimiento.springItv.entities.NeumaticoEntity;

import java.util.List;

public interface NeumaticoRepository extends JpaRepository<NeumaticoEntity, Integer>{

    List<NeumaticoEntity> findByCocheMatricula(String matricula);

}
