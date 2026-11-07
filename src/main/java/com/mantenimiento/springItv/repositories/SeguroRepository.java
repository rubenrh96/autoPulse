package com.mantenimiento.springItv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.mantenimiento.springItv.entities.SeguroEntity;

import java.util.Date;
import java.util.List;

public interface SeguroRepository extends JpaRepository<SeguroEntity, Integer> {

    List<SeguroEntity> findByCocheMatricula(String matricula);

    // Ver nota en ItvRepository/RecambioRepository sobre por qué se evita deleteById() con @Id primitivo.
    @Modifying
    @Transactional
    @Query("DELETE FROM SeguroEntity s WHERE s.idSeguro = :id")
    void deleteByIdSeguro(Integer id);

    // Mismo patrón que ItvRepository.findGastosPorUsuario, para que el seguro entre en los
    // gráficos (gasto total, gasto mensual, coste por tipo) junto al resto de gastos del coche.
    @Query("""
        SELECT s.coche.matricula, s.fechaInicio, s.precio
        FROM SeguroEntity s
        WHERE s.coche.usuario.username = :username
          AND s.fechaInicio >= :desde
          AND s.fechaInicio <= :hasta
        """)
    List<Object[]> findGastosPorUsuario(String username, Date desde, Date hasta);

}
