package com.mantenimiento.springItv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mantenimiento.springItv.entities.ItvEntity;

public interface ItvRepository extends JpaRepository<ItvEntity, Integer>{

}
