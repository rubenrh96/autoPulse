package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.entities.*;
import com.mantenimiento.springItv.models.Coche;
import com.mantenimiento.springItv.models.Itv;
import com.mantenimiento.springItv.services.*;
import com.mantenimiento.springItv.transformadores.TransformadorCoche;
import com.mantenimiento.springItv.transformadores.TransformadorItv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/coches")
public class CocheController {

    @Autowired
    private CocheService cocheService;

    @Autowired
    private RecambioService recambioService;

    @Autowired
    private MantenimientoService mantenimientoService;

    @Autowired
    private ItvService itvService;

    @GetMapping
    public String listarCoches(Model model) {
        List<CocheEntity> listaCoches = cocheService.listarCoches();
        List<Coche> listaCochesModelo = listaCoches.stream()
                .map(TransformadorCoche::cocheEntityToCoche)
                .collect(Collectors.toList());

        List<RecambioEntity> listaRecambios = recambioService.listarRecambios();
        model.addAttribute("coches", listaCochesModelo);
        model.addAttribute("recambios", listaRecambios);
        return "coche/listaCoches";
    }

    @PostMapping("/{matricula}")
    public String updateKilometraje(@PathVariable String matricula, @RequestParam("kilometros") int kilometros) {
        cocheService.obtenerPorId(matricula).ifPresent(coche -> {
            coche.setKilometros(kilometros);
            cocheService.guardarCoche(coche);
        });
        return "redirect:/coches/" + matricula;
    }

    @GetMapping("/{matricula}")
    public String verDetalles(@PathVariable String matricula, Model model) {
        cocheService.obtenerPorId(matricula).ifPresent(c -> model.addAttribute("coche", c));
        return "coche/detallesCoche";
    }

    @PostMapping
    public ResponseEntity<CocheEntity> crearCoche(@RequestBody CocheEntity coche) {
        CocheEntity nuevoCoche = cocheService.guardarCoche(coche);
        return ResponseEntity.status(201).body(nuevoCoche);
    }

    @GetMapping("/{matricula}/modal")
    public ResponseEntity<Map<String, String>> mostrarMensajeModal(@PathVariable String matricula) {
        List<MantenimientoEntity> mantenimientos = mantenimientoService.listarMantenimientos(matricula);
        List<MantenimientoEntity> listaMotor = new ArrayList<>();
        List<MantenimientoEntity> listaCambio = new ArrayList<>();
        String mensajeMotor = "", mensajeCambio = "";

        for (MantenimientoEntity mantenimiento : mantenimientos) {
            if (Long.valueOf(2).equals(mantenimiento.getCategoria().getIdCategoria())) {
                listaMotor.add(mantenimiento);
            } else if (Long.valueOf(3).equals(mantenimiento.getCategoria().getIdCategoria())) {
                listaCambio.add(mantenimiento);
            }
        }

        listaMotor.sort(Comparator.comparing(MantenimientoEntity::getFecha).reversed());
        listaCambio.sort(Comparator.comparing(MantenimientoEntity::getFecha).reversed());

        if (!listaMotor.isEmpty()) {
            int km = listaMotor.get(0).getKmMantenimiento();
            int diferencia = (km + 20000) - listaMotor.get(0).getCoche().getKilometros();
            mensajeMotor = "Quedan <strong>" + diferencia + " km</strong> para cambiar el <strong>aceite del motor.</strong>";
        }

        if (!listaCambio.isEmpty()) {
            int km = listaCambio.get(0).getKmMantenimiento();
            int diferencia = (km + 60000) - listaCambio.get(0).getCoche().getKilometros();
            mensajeCambio = "Quedan <strong>" + diferencia + " km</strong> para cambiar el <strong>aceite del cambio.</strong>";
        }

        List<ItvEntity> listaItvs = itvService.listarItvs(matricula);
        List<Itv> listaItvsModel = listaItvs.stream()
                .map(TransformadorItv::itvEntityToItv)
                .sorted(Comparator.comparing(Itv::getKmRevision).reversed())
                .collect(Collectors.toList());

        Map<String, String> response = new HashMap<>();
        response.put("mensajeModalMotor", mensajeMotor);
        response.put("mensajeModalCambio", mensajeCambio);

        if (!listaItvsModel.isEmpty()) {
            Itv itv = listaItvsModel.get(0);
            String fechaFormateada = new SimpleDateFormat("dd-MM-yyyy").format(itv.getFechaProximaItv());
            response.put("mensajeModalItv", "La fecha de próxima ITV es: <strong>" + fechaFormateada + "</strong>");
        }

        return ResponseEntity.ok(response);
    }
}
