package com.mantenimiento.springItv.services;

import com.mantenimiento.springItv.dto.AlertaVehiculoDto;
import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.entities.ItvEntity;
import com.mantenimiento.springItv.entities.MantenimientoEntity;
import com.mantenimiento.springItv.entities.SeguroEntity;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlertasCalculatorTest {

    private static final LocalDate HOY = LocalDate.of(2026, 11, 7);

    private static CocheEntity coche(int km) {
        CocheEntity c = new CocheEntity();
        c.setMatricula("1234ABC");
        c.setKilometros(km);
        return c;
    }

    private static ItvEntity itv(LocalDate apto, LocalDate proxima) {
        ItvEntity i = new ItvEntity();
        i.setFechaApto(Date.valueOf(apto));
        i.setFechaProximaItv(proxima);
        return i;
    }

    private static SeguroEntity seguro(LocalDate inicio, LocalDate renovacion) {
        SeguroEntity s = new SeguroEntity();
        s.setFechaInicio(Date.valueOf(inicio));
        s.setFechaRenovacion(renovacion);
        return s;
    }

    private static MantenimientoEntity mant(String desc, int km) {
        MantenimientoEntity m = new MantenimientoEntity();
        m.setDescripcion(desc);
        m.setKmMantenimiento(km);
        m.setFecha(Date.valueOf(HOY.minusMonths(6)));
        return m;
    }

    private static AlertaVehiculoDto de(List<AlertaVehiculoDto> l, String tipo) {
        return l.stream().filter(a -> a.getTipo().equals(tipo)).findFirst().orElseThrow();
    }

    @Test
    void sinDatosNoGeneraNada() {
        assertTrue(AlertasCalculator.calcular(coche(1000), List.of(), List.of(), List.of(), HOY).isEmpty());
    }

    @Test
    void umbralesDeDias() {
        assertEquals("danger", AlertasCalculator.urgenciaPorDias(-3));
        assertEquals("danger", AlertasCalculator.urgenciaPorDias(6));
        assertEquals("warning", AlertasCalculator.urgenciaPorDias(7));
        assertEquals("warning", AlertasCalculator.urgenciaPorDias(30));
        assertEquals("ok", AlertasCalculator.urgenciaPorDias(31));
    }

    @Test
    void umbralesDeKm() {
        assertEquals("danger", AlertasCalculator.urgenciaPorKm(-1));
        assertEquals("danger", AlertasCalculator.urgenciaPorKm(499));
        assertEquals("warning", AlertasCalculator.urgenciaPorKm(500));
        assertEquals("warning", AlertasCalculator.urgenciaPorKm(1499));
        assertEquals("ok", AlertasCalculator.urgenciaPorKm(1500));
    }

    @Test
    void itvUsaLaUltimaRevisionNoLaProximaMasLejana() {
        List<ItvEntity> itvs = List.of(
                itv(HOY.minusYears(3), HOY.minusYears(2)),   // antigua, caducada
                itv(HOY.minusMonths(1), HOY.plusDays(20)));  // la última: ésta manda
        AlertaVehiculoDto a = de(AlertasCalculator.calcular(coche(0), itvs, List.of(), List.of(), HOY), "ITV");
        assertEquals(20, a.getDiasRestantes());
        assertEquals("warning", a.getUrgencia());
        assertEquals(HOY.plusDays(20).toString(), a.getFechaObjetivo());
    }

    @Test
    void itvVencidaEsDangerConDiasNegativos() {
        AlertaVehiculoDto a = de(AlertasCalculator.calcular(coche(0),
                List.of(itv(HOY.minusYears(1), HOY.minusDays(4))), List.of(), List.of(), HOY), "ITV");
        assertEquals(-4, a.getDiasRestantes());
        assertEquals("danger", a.getUrgencia());
    }

    @Test
    void itvSinFechaProximaSeIgnora() {
        assertTrue(AlertasCalculator.calcular(coche(0), List.of(itv(HOY, null)), List.of(), List.of(), HOY).isEmpty());
    }

    @Test
    void seguroUsaLaUltimaPolizaYSuRenovacion() {
        List<SeguroEntity> seguros = List.of(
                seguro(HOY.minusYears(2), HOY.minusYears(1)),
                seguro(HOY.minusMonths(11), HOY.plusDays(40)));
        AlertaVehiculoDto a = de(AlertasCalculator.calcular(coche(0), List.of(), seguros, List.of(), HOY), "SEGURO");
        assertEquals(40, a.getDiasRestantes());
        assertEquals("ok", a.getUrgencia());
    }

    @Test
    void aceiteMotorCuentaDesdeElUltimoCambioPorKm() {
        // último cambio a 100.000 -> toca a 120.000; coche a 119.000 -> quedan 1.000 -> warning
        List<MantenimientoEntity> ms = List.of(mant("Cambio de aceite y filtro", 80_000), mant("Cambio aceite motor", 100_000));
        AlertaVehiculoDto a = de(AlertasCalculator.calcular(coche(119_000), List.of(), List.of(), ms, HOY), "ACEITE_MOTOR");
        assertEquals(120_000, a.getKmObjetivo());
        assertEquals(1_000, a.getKmRestantes());
        assertEquals(100_000, a.getUltimoKm());
        assertEquals("warning", a.getUrgencia());
    }

    @Test
    void aceiteMotorPasadoDeKmEsDanger() {
        AlertaVehiculoDto a = de(AlertasCalculator.calcular(coche(125_000), List.of(), List.of(),
                List.of(mant("Aceite", 100_000)), HOY), "ACEITE_MOTOR");
        assertEquals(-5_000, a.getKmRestantes());
        assertEquals("danger", a.getUrgencia());
    }

    @Test
    void aceiteDeCajaSeSeparaDelDeMotor() {
        List<MantenimientoEntity> ms = List.of(
                mant("Cambio de aceite de la caja de cambios", 50_000),
                mant("Aceite transmisión automática", 40_000),
                mant("Aceite motor", 90_000));
        List<AlertaVehiculoDto> res = AlertasCalculator.calcular(coche(100_000), List.of(), List.of(), ms, HOY);
        assertEquals(2, res.size());
        assertEquals(110_000, de(res, "ACEITE_CAJA").getKmObjetivo());   // 50.000 + 60.000
        assertEquals(110_000, de(res, "ACEITE_MOTOR").getKmObjetivo());  // 90.000 + 20.000
    }

    private static MantenimientoEntity mantCat(String desc, String categoria, int km) {
        MantenimientoEntity m = mant(desc, km);
        com.mantenimiento.springItv.entities.CategoriaEntity c = new com.mantenimiento.springItv.entities.CategoriaEntity();
        c.setDescripcion(categoria);
        m.setCategoria(c);
        return m;
    }

    @Test
    void categoriaCambioDeAceiteDelCambioCuentaComoCajaAunqueLaDescripcionNoLoDiga() {
        List<MantenimientoEntity> ms = List.of(
                mantCat("Revisión", "Cambio de aceite del cambio", 100_000),
                mantCat("Aceite y filtro", "Cambio de aceite del motor", 105_000));
        List<AlertaVehiculoDto> res = AlertasCalculator.calcular(coche(150_000), List.of(), List.of(), ms, HOY);
        assertEquals(160_000, de(res, "ACEITE_CAJA").getKmObjetivo());   // 100.000 + 60.000
        assertEquals(125_000, de(res, "ACEITE_MOTOR").getKmObjetivo());  // 105.000 + 20.000
    }

    @Test
    void aceiteDelCambioNoEnsuciaElContadorDelMotor() {
        // Sin esto el cambio de caja reiniciaba el aviso del motor.
        List<MantenimientoEntity> ms = List.of(
                mantCat("Aceite motor", "Cambio de aceite", 90_000),
                mantCat("Cambio de aceite del cambio", "Cambio de aceite del cambio", 110_000));
        List<AlertaVehiculoDto> res = AlertasCalculator.calcular(coche(115_000), List.of(), List.of(), ms, HOY);
        assertEquals(110_000, de(res, "ACEITE_MOTOR").getKmObjetivo());  // 90.000 + 20.000
        assertEquals(170_000, de(res, "ACEITE_CAJA").getKmObjetivo());
    }

    @Test
    void cocheManualSinAceiteDeCajaNoGeneraAlertaDeCaja() {
        List<AlertaVehiculoDto> res = AlertasCalculator.calcular(coche(50_000), List.of(), List.of(),
                List.of(mantCat("Aceite", "Cambio de aceite", 40_000)), HOY);
        assertEquals(List.of("ACEITE_MOTOR"), res.stream().map(AlertaVehiculoDto::getTipo).toList());
    }

    @Test
    void mantenimientoSinAceiteONuloNoGeneraAlerta() {
        MantenimientoEntity sinDesc = new MantenimientoEntity();
        sinDesc.setKmMantenimiento(1);
        assertTrue(AlertasCalculator.calcular(coche(100_000), List.of(), List.of(),
                List.of(mant("Frenos delanteros", 90_000), sinDesc), HOY).isEmpty());
    }

    @Test
    void ordenNaturalItvSeguroMotorCaja() {
        List<AlertaVehiculoDto> res = AlertasCalculator.calcular(coche(10),
                List.of(itv(HOY, HOY.plusYears(1))),
                List.of(seguro(HOY, HOY.plusYears(1))),
                List.of(mant("aceite caja", 0), mant("aceite motor", 0)), HOY);
        assertEquals(List.of("ITV", "SEGURO", "ACEITE_MOTOR", "ACEITE_CAJA"),
                res.stream().map(AlertaVehiculoDto::getTipo).toList());
    }
}
