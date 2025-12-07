package com.mantenimiento.springItv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mantenimiento.springItv.entities.CocheEntity;
import org.springframework.data.jpa.repository.*;
import javax.transaction.Transactional;
import java.util.List;

public interface CocheRepository extends JpaRepository<CocheEntity, String> {

    List<CocheEntity> findByUsuarioId(Long usuarioId);

    @Modifying
    @Transactional
    @Query("""
        UPDATE CocheEntity c
        SET c.kilometros = :nuevoKm
        WHERE c.matricula  = :matricula
          AND (:nuevoKm > c.kilometros)
    """)
    void actualizarKilometraje(String matricula, Integer nuevoKm);
}
