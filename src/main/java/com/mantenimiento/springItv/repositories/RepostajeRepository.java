package com.mantenimiento.springItv.repositories;

import com.mantenimiento.springItv.dto.GastoKmPorMesDto;
import com.mantenimiento.springItv.dto.GastoMensualDto;
import com.mantenimiento.springItv.dto.GastoPorCocheDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import com.mantenimiento.springItv.entities.RepostajeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface RepostajeRepository extends JpaRepository<RepostajeEntity, Integer> {

        List<RepostajeEntity> findByCocheMatricula(String matricula);

        // Ver nota en RecambioRepository sobre por qué se evita deleteById() con @Id primitivo.
        @Modifying
        @Transactional
        @Query("DELETE FROM RepostajeEntity r WHERE r.idRepostaje = :id")
        void deleteByIdRepostaje(Integer id);

        // Object[] = {matricula, fecha, precio}; ver nota en ItvRepository.
        @Query("""
           SELECT r.coche.matricula, r.fecha, r.precio
           FROM RepostajeEntity r
           WHERE r.coche.usuario.username = :username
             AND r.fecha >= :desde
             AND r.fecha <= :hasta
           """)
        List<Object[]> findGastosPorUsuario(String username, Date desde, Date hasta);

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
