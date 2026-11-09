package com.mantenimiento.springItv.services;

import com.mantenimiento.springItv.dto.AlertaVehiculoDto;
import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.entities.ItvEntity;
import com.mantenimiento.springItv.entities.MantenimientoEntity;
import com.mantenimiento.springItv.entities.SeguroEntity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Lógica pura (sin Spring ni BD) de las alertas de un vehículo. Aquí viven los umbrales y los
 * intervalos: es el único sitio a tocar para cambiarlos.
 */
public final class AlertasCalculator {

    public static final String OK = "ok";
    public static final String WARNING = "warning";
    public static final String DANGER = "danger";

    public static final int INTERVALO_ACEITE_MOTOR_KM = 20_000;
    public static final int INTERVALO_ACEITE_CAJA_KM = 60_000;

    // El backend no tiene un "tipo" estructurado de mantenimiento: se identifica por texto, mirando
    // la descripción Y el nombre de la categoría (p. ej. la categoría "Cambio de aceite del cambio").
    // "del cambio" / "de cambio" = caja de cambios; "cambio de aceite" NO debe contar como caja.
    private static final Pattern CAJA = Pattern.compile(
            "(caja|autom[aá]tic|transmisi[oó]n|del cambio|de cambio|cambio de marchas|dsg|valvulina)");

    private AlertasCalculator() {}

    public static String urgenciaPorDias(long dias) {
        if (dias < 7) return DANGER;      // incluye vencidas
        if (dias <= 30) return WARNING;
        return OK;
    }

    public static String urgenciaPorKm(long km) {
        if (km < 500) return DANGER;      // incluye pasadas
        if (km < 1500) return WARNING;
        return OK;
    }

    /** Orden natural: ITV, seguro, aceite motor, aceite caja. Solo incluye lo que tiene datos reales. */
    public static List<AlertaVehiculoDto> calcular(CocheEntity coche,
                                                   List<ItvEntity> itvs,
                                                   List<SeguroEntity> seguros,
                                                   List<MantenimientoEntity> mantenimientos,
                                                   LocalDate hoy) {
        List<AlertaVehiculoDto> res = new ArrayList<>();
        String mat = coche.getMatricula();

        itvs.stream()
                .filter(i -> i.getFechaProximaItv() != null)
                .max(Comparator.comparing((ItvEntity i) -> aLocalDate(i.getFechaApto()),
                        Comparator.nullsFirst(Comparator.naturalOrder())))
                .ifPresent(i -> res.add(porFecha(mat, "ITV", "ITV", i.getFechaProximaItv(), hoy)));

        seguros.stream()
                .filter(s -> s.getFechaRenovacion() != null)
                .max(Comparator.comparing((SeguroEntity s) -> aLocalDate(s.getFechaInicio()),
                        Comparator.nullsFirst(Comparator.naturalOrder())))
                .ifPresent(s -> res.add(porFecha(mat, "SEGURO", "Seguro", s.getFechaRenovacion(), hoy)));

        ultimoAceite(mantenimientos, false).ifPresent(m -> res.add(
                porKm(mat, "ACEITE_MOTOR", "Cambio de aceite (motor)", m, INTERVALO_ACEITE_MOTOR_KM, coche.getKilometros())));
        ultimoAceite(mantenimientos, true).ifPresent(m -> res.add(
                porKm(mat, "ACEITE_CAJA", "Cambio de aceite (caja)", m, INTERVALO_ACEITE_CAJA_KM, coche.getKilometros())));

        return res;
    }

    private static AlertaVehiculoDto porFecha(String mat, String tipo, String titulo, LocalDate objetivo, LocalDate hoy) {
        long dias = ChronoUnit.DAYS.between(hoy, objetivo);
        return AlertaVehiculoDto.builder()
                .id(mat + "-" + tipo.toLowerCase())
                .matricula(mat).tipo(tipo).titulo(titulo)
                .fechaObjetivo(objetivo.toString())
                .diasRestantes((int) dias)
                .urgencia(urgenciaPorDias(dias))
                .build();
    }

    private static AlertaVehiculoDto porKm(String mat, String tipo, String titulo, MantenimientoEntity ultimo,
                                           int intervalo, int kmActuales) {
        int objetivo = ultimo.getKmMantenimiento() + intervalo;
        int restantes = objetivo - kmActuales;
        LocalDate ultimaFecha = aLocalDate(ultimo.getFecha());
        return AlertaVehiculoDto.builder()
                .id(mat + "-" + tipo.toLowerCase())
                .matricula(mat).tipo(tipo).titulo(titulo)
                .kmObjetivo(objetivo).kmRestantes(restantes)
                .ultimoKm(ultimo.getKmMantenimiento())
                .ultimaFecha(ultimaFecha != null ? ultimaFecha.toString() : null)
                .urgencia(urgenciaPorKm(restantes))
                .build();
    }

    private static Optional<MantenimientoEntity> ultimoAceite(List<MantenimientoEntity> mants, boolean caja) {
        return mants.stream()
                .filter(m -> {
                    String d = textoDe(m);
                    return d.contains("aceite") && (CAJA.matcher(d).find() == caja);
                })
                .max(Comparator.comparingInt(MantenimientoEntity::getKmMantenimiento));
    }

    private static String textoDe(MantenimientoEntity m) {
        String desc = m.getDescripcion() != null ? m.getDescripcion() : "";
        String cat = m.getCategoria() != null && m.getCategoria().getDescripcion() != null
                ? m.getCategoria().getDescripcion() : "";
        return (desc + " " + cat).toLowerCase(Locale.ROOT);
    }

    // java.sql.Date#toInstant lanza UnsupportedOperationException: hay que pasar por el timestamp.
    static LocalDate aLocalDate(Date d) {
        if (d == null) return null;
        if (d instanceof java.sql.Date sql) return sql.toLocalDate();
        return new Timestamp(d.getTime()).toLocalDateTime().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
