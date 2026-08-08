package com.mantenimiento.springItv.services;

import com.mantenimiento.springItv.dto.GastoLineaDto;
import com.mantenimiento.springItv.dto.GastoPorCocheDto;
import com.mantenimiento.springItv.repositories.ItvRepository;
import com.mantenimiento.springItv.repositories.MantenimientoRepository;
import com.mantenimiento.springItv.repositories.NeumaticoRepository;
import com.mantenimiento.springItv.repositories.RecambioRepository;
import com.mantenimiento.springItv.repositories.RepostajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

// Agrega el gasto real (ITV + Mantenimiento + Repostaje + Neumáticos + Recambios) para los
// gráficos y KPIs, con filtro de fechas opcional (desde/hasta inclusive).
@Service
@RequiredArgsConstructor
public class GraficoService {

    private final ItvRepository itvRepository;
    private final MantenimientoRepository mantenimientoRepository;
    private final RepostajeRepository repostajeRepository;
    private final NeumaticoRepository neumaticoRepository;
    private final RecambioRepository recambioRepository;

    // Nunca se pasa NULL como parámetro de fecha a las queries: enlazar un parámetro NULL en una
    // comparación JPQL (":desde IS NULL OR campo >= :desde") es una fuente conocida de fallos en
    // tiempo de ejecución en Hibernate porque no siempre puede inferir el tipo SQL de un NULL sin
    // contexto. En su lugar, "sin filtro" se representa con un rango centinela que cubre cualquier
    // fecha real de la aplicación.
    private static final Date FECHA_MINIMA = new Date(0); // 1970-01-01
    private static final Date FECHA_MAXIMA = Date.from(LocalDate.of(2100, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());

    private Date aFechaInicio(LocalDate desde) {
        return desde == null ? FECHA_MINIMA : Date.from(desde.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private Date aFechaFin(LocalDate hasta) {
        return hasta == null
                ? FECHA_MAXIMA
                : Date.from(hasta.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().minusMillis(1));
    }

    // Cada fila es {matricula, fecha, precio} (Recambio no tiene matricula, ver más abajo).
    // Se mapea a mano en vez de usar una expresión de constructor JPQL (new GastoLineaDto(...))
    // porque mezclar el precio double primitivo de las entidades con el Double del DTO falla
    // en tiempo de ejecución en esta versión de Hibernate.
    private void agregarLineas(List<GastoLineaDto> destino, List<Object[]> filas, String tipo) {
        for (Object[] fila : filas) {
            String matricula = (String) fila[0];
            Date fecha = (Date) fila[1];
            Number precio = (Number) fila[2];
            destino.add(new GastoLineaDto(matricula, fecha, precio == null ? 0.0 : precio.doubleValue(), tipo));
        }
    }

    private List<GastoLineaDto> obtenerTodosLosGastos(String username, LocalDate desde, LocalDate hasta) {
        Date desdeDate = aFechaInicio(desde);
        Date hastaDate = aFechaFin(hasta);

        List<GastoLineaDto> gastos = new ArrayList<>();
        agregarLineas(gastos, itvRepository.findGastosPorUsuario(username, desdeDate, hastaDate), "ITV");
        agregarLineas(gastos, mantenimientoRepository.findGastosPorUsuario(username, desdeDate, hastaDate), "Mantenimiento");
        agregarLineas(gastos, repostajeRepository.findGastosPorUsuario(username, desdeDate, hastaDate), "Repostaje");
        agregarLineas(gastos, neumaticoRepository.findGastosPorUsuario(username, desdeDate, hastaDate), "Neumáticos");

        for (Object[] fila : recambioRepository.findGastosPorUsuario(username, desdeDate, hastaDate)) {
            Date fecha = (Date) fila[0];
            BigDecimal precio = (BigDecimal) fila[1];
            gastos.add(new GastoLineaDto(null, fecha, precio == null ? 0.0 : precio.doubleValue(), "Recambios"));
        }

        return gastos;
    }

    public List<GastoPorCocheDto> gastoPorCoche(String username, LocalDate desde, LocalDate hasta) {
        return obtenerTodosLosGastos(username, desde, hasta).stream()
                .filter(g -> g.getMatricula() != null)
                .collect(Collectors.groupingBy(GastoLineaDto::getMatricula, Collectors.summingDouble(GastoLineaDto::getPrecio)))
                .entrySet().stream()
                .map(e -> new GastoPorCocheDto(e.getKey(), e.getValue()))
                .sorted((a, b) -> Double.compare(b.getTotal(), a.getTotal()))
                .collect(Collectors.toList());
    }

    public Map<String, Object> costePorTipo(String username, LocalDate desde, LocalDate hasta) {
        Map<String, Double> porTipo = new LinkedHashMap<>();
        for (GastoLineaDto g : obtenerTodosLosGastos(username, desde, hasta)) {
            porTipo.merge(g.getTipo(), g.getPrecio(), Double::sum);
        }
        return Map.of("labels", new ArrayList<>(porTipo.keySet()), "data", new ArrayList<>(porTipo.values()));
    }

    public Map<String, Object> gastoMensual(String username, LocalDate desde, LocalDate hasta) {
        SimpleDateFormat mesFormat = new SimpleDateFormat("yyyy-MM");
        Map<String, Double> porMes = new TreeMap<>();
        for (GastoLineaDto g : obtenerTodosLosGastos(username, desde, hasta)) {
            String mes = mesFormat.format(g.getFecha());
            porMes.merge(mes, g.getPrecio(), Double::sum);
        }
        return Map.of("labels", new ArrayList<>(porMes.keySet()), "data", new ArrayList<>(porMes.values()));
    }

    public double gastoTotal(String username, LocalDate desde, LocalDate hasta) {
        return obtenerTodosLosGastos(username, desde, hasta).stream()
                .mapToDouble(GastoLineaDto::getPrecio)
                .sum();
    }
}
