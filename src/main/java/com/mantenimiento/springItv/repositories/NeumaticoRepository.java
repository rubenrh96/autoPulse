package com.mantenimiento.springItv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.mantenimiento.springItv.entities.NeumaticoEntity;

import java.util.Date;
import java.util.List;

public interface NeumaticoRepository extends JpaRepository<NeumaticoEntity, Integer>{

    List<NeumaticoEntity> findByCocheMatricula(String matricula);

    // Ver nota en RecambioRepository sobre por qué se evita deleteById() con @Id primitivo.
    @Modifying
    @Transactional
    @Query("DELETE FROM NeumaticoEntity n WHERE n.idNeumatico = :id")
    void deleteByIdNeumatico(Integer id);

    // Object[] = {matricula, fechaMontaje, precio}; ver nota en ItvRepository.
    @Query("""
        SELECT n.coche.matricula, n.fechaMontaje, n.precio
        FROM NeumaticoEntity n
        WHERE n.coche.usuario.username = :username
          AND n.fechaMontaje >= :desde
          AND n.fechaMontaje <= :hasta
        """)
    List<Object[]> findGastosPorUsuario(String username, Date desde, Date hasta);

}
