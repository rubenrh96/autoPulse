package com.mantenimiento.springItv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.mantenimiento.springItv.entities.RecambioEntity;

import java.util.Date;
import java.util.List;

public interface RecambioRepository extends JpaRepository<RecambioEntity, Integer>{

    List<RecambioEntity> findByUsuarioId(Long usuarioId);

    // deleteById() estándar pasa por entityInformation.isNew(), que para @Id primitivo
    // puede no comportarse de forma fiable y dejar el borrado sin persistir (204 pero sin
    // efecto real). DELETE directo evita ese camino.
    @Modifying
    @Transactional
    @Query("DELETE FROM RecambioEntity r WHERE r.idRecambio = :id")
    void deleteByIdRecambio(Integer id);

    // Sin coche.matricula (un recambio no está atado a un vehículo) ni GastoLineaDto directo
    // (precio es BigDecimal, no double) — se mapea a mano en GraficoService.
    @Query("""
        SELECT r.fechaCompra, r.precio
        FROM RecambioEntity r
        WHERE r.usuario.username = :username
          AND r.fechaCompra >= :desde
          AND r.fechaCompra <= :hasta
        """)
    List<Object[]> findGastosPorUsuario(String username, Date desde, Date hasta);

}
