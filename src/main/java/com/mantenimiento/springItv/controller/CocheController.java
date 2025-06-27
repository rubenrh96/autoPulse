package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.entities.*;
import com.mantenimiento.springItv.models.Coche;
import com.mantenimiento.springItv.models.Itv;
import com.mantenimiento.springItv.models.Recambio;
import com.mantenimiento.springItv.secutity.CustomUserDetails;
import com.mantenimiento.springItv.services.*;
import com.mantenimiento.springItv.transformadores.TransformadorCoche;
import com.mantenimiento.springItv.transformadores.TransformadorItv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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
    public String listarCoches(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        UsuarioEntity usuario = user.getUsuario();

        List<CocheEntity> listaCoches = cocheService.listarCochesPorUsuario(usuario.getId());
        List<Coche> listaCochesModelo = listaCoches.stream()
                .map(TransformadorCoche::cocheEntityToCoche)
                .collect(Collectors.toList());
        model.addAttribute("coches", listaCochesModelo);
        return "coche/listaCoches";
    }


    @PostMapping("/{matricula}/kilometros")
    public String actualizarKilometraje(@PathVariable String matricula,
                                        @RequestParam int kilometros,
                                        RedirectAttributes attrs) {

        cocheService.obtenerPorId(matricula).ifPresent(c -> {
            c.setKilometros(kilometros);
            cocheService.guardarCoche(c);
        });

        attrs.addFlashAttribute("success", "Kilometraje actualizado");
        return "redirect:/coches/" + matricula + "?success=kilometraje" ;
    }


    @GetMapping("/{matricula}")
    public String verDetalles(@PathVariable String matricula, Model model) {
        cocheService.obtenerPorId(matricula).ifPresent(c -> model.addAttribute("coche", c));
        return "coche/detallesCoche";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioAlta(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }

        CocheEntity coche = new CocheEntity();
        coche.setUsuario(usuario);
        model.addAttribute("coche", coche);
        return "coche/agregarCoche";
    }

    @PostMapping
    public String guardarCoche(@ModelAttribute("coche") CocheEntity coche, @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }

        coche.setUsuario(usuario);
        cocheService.guardarCoche(coche);
        return "redirect:/coches" + "?success=coche";
    }
}
