package com.mantenimiento.springItv.repositories;

import com.mantenimiento.springItv.dto.GastoKmPorMesDto;
import com.mantenimiento.springItv.dto.GastoMensualDto;
import com.mantenimiento.springItv.dto.GastoPorCocheDto;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mantenimiento.springItv.entities.RepostajeEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepostajeRepository extends JpaRepository<RepostajeEntity, Integer> {

        @Query("""
           SELECT new com.mantenimiento.springItv.dto.GastoPorCocheDto(
                     r.coche.matricula,
                     SUM(r.precio)
           )
           FROM RepostajeEntity r
           WHERE r.coche.usuario.username = :username
           GROUP BY r.coche.matricula
           """)
        List<GastoPorCocheDto> findGastoTotalPorCoche(String username);

        @Query("SELECT new com.mantenimiento.springItv.dto.GastoMensualDto(r.coche.matricula, FUNCTION('TO_CHAR', r.fecha, 'YYYY-MM'), SUM(r.precio)) " +
                "FROM RepostajeEntity r " +
                "WHERE r.coche.usuario.username = :username " +
                "GROUP BY r.coche.matricula, FUNCTION('TO_CHAR', r.fecha, 'YYYY-MM') " +
                "ORDER BY FUNCTION('TO_CHAR', r.fecha, 'YYYY-MM')")
        List<GastoMensualDto> findGastoMensualPorCoche(String username);
}
