package com.mantenimiento.springItv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.mantenimiento.springItv.entities.ItvEntity;

import java.util.Date;
import java.util.List;

public interface ItvRepository extends JpaRepository<ItvEntity, Integer>{

    List<ItvEntity> findByCocheMatricula(String matricula);

    // Object[] = {matricula, fechaApto, precio}. Se evita la expresión de constructor JPQL
    // (new GastoLineaDto(...)) porque mezclar el precio double primitivo con el Double del DTO
    // falla en tiempo de ejecución en esta versión de Hibernate; se mapea a mano en GraficoService.
    @Query("""
        SELECT i.coche.matricula, i.fechaApto, i.precio
        FROM ItvEntity i
        WHERE i.coche.usuario.username = :username
          AND (:desde IS NULL OR i.fechaApto >= :desde)
          AND (:hasta IS NULL OR i.fechaApto <= :hasta)
        """)
    List<Object[]> findGastosPorUsuario(String username, Date desde, Date hasta);

}
