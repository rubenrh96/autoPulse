package com.mantenimiento.springItv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.mantenimiento.springItv.dto.GastoLineaDto;
import com.mantenimiento.springItv.entities.NeumaticoEntity;

import java.util.Date;
import java.util.List;

public interface NeumaticoRepository extends JpaRepository<NeumaticoEntity, Integer>{

    List<NeumaticoEntity> findByCocheMatricula(String matricula);

    @Query("""
        SELECT new com.mantenimiento.springItv.dto.GastoLineaDto(n.coche.matricula, n.fechaMontaje, n.precio, 'Neumáticos')
        FROM NeumaticoEntity n
        WHERE n.coche.usuario.username = :username
          AND (:desde IS NULL OR n.fechaMontaje >= :desde)
          AND (:hasta IS NULL OR n.fechaMontaje <= :hasta)
        """)
    List<GastoLineaDto> findGastosPorUsuario(String username, Date desde, Date hasta);

}
