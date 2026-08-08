package com.mantenimiento.springItv.repositories;

import com.mantenimiento.springItv.dto.CostePorCategoriaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mantenimiento.springItv.entities.MantenimientoEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface MantenimientoRepository extends JpaRepository<MantenimientoEntity, Integer>, JpaSpecificationExecutor<MantenimientoEntity> {

    @Query("SELECT new com.mantenimiento.springItv.dto.CostePorCategoriaDto(m.categoria.descripcion, SUM(m.precio)) " +
            "FROM MantenimientoEntity m " +
            "WHERE m.coche.usuario.username = :username " +
            "GROUP BY m.categoria.descripcion")
    List<CostePorCategoriaDto> findCostePorCategoria(String username);

    // Object[] = {matricula, fecha, precio}; ver nota en ItvRepository sobre por qué no se usa
    // una expresión de constructor JPQL aquí.
    @Query("""
        SELECT m.coche.matricula, m.fecha, m.precio
        FROM MantenimientoEntity m
        WHERE m.coche.usuario.username = :username
          AND m.fecha >= :desde
          AND m.fecha <= :hasta
        """)
    List<Object[]> findGastosPorUsuario(String username, Date desde, Date hasta);

}
