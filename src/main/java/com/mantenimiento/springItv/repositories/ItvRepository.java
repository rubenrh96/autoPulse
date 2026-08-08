package com.mantenimiento.springItv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.mantenimiento.springItv.dto.GastoLineaDto;
import com.mantenimiento.springItv.entities.ItvEntity;

import java.util.Date;
import java.util.List;

public interface ItvRepository extends JpaRepository<ItvEntity, Integer>{

    List<ItvEntity> findByCocheMatricula(String matricula);

    @Query("""
        SELECT new com.mantenimiento.springItv.dto.GastoLineaDto(i.coche.matricula, i.fechaApto, i.precio, 'ITV')
        FROM ItvEntity i
        WHERE i.coche.usuario.username = :username
          AND (:desde IS NULL OR i.fechaApto >= :desde)
          AND (:hasta IS NULL OR i.fechaApto <= :hasta)
        """)
    List<GastoLineaDto> findGastosPorUsuario(String username, Date desde, Date hasta);

}
