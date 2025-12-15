package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.dto.GastoMensualDto;
import com.mantenimiento.springItv.entities.*;
import com.mantenimiento.springItv.models.Coche;
import com.mantenimiento.springItv.secutity.CustomUserDetails;
import com.mantenimiento.springItv.services.*;
import com.mantenimiento.springItv.transformadores.TransformadorCoche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/coches")
public class CocheController {

    @Autowired
    private CocheService cocheService;

    @Autowired
    private RepostajeService repostajeService;

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

        // ► Resumen: número de coches, próxima ITV y gasto de repostajes del mes actual
        model.addAttribute("resumenNumCoches", listaCochesModelo.size());

        List<LocalDate> fechasItv = itvService.listarItvPorUsuario(usuario.getId());
        Optional<LocalDate> proximaItv = fechasItv.stream()
                .filter(fecha -> fecha != null)
                .min(Comparator.naturalOrder());
        proximaItv.ifPresent(fecha -> model.addAttribute("resumenProximaItv", fecha));

        YearMonth ahora = YearMonth.now();
        double gastoMes = repostajeService.gastoMensualPorCocheDe(user.getUsername()).stream()
                .filter(dto -> {
                    String mes = dto.getMes(); // formato YYYY-MM
                    return mes != null && mes.startsWith(ahora.toString());
                })
                .mapToDouble(GastoMensualDto::getTotal)
                .sum();
        model.addAttribute("resumenGastoMes", gastoMes);

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
        cocheService.obtenerPorId(matricula).ifPresent(c -> {
            model.addAttribute("coche", c);

            c.getItvs().stream()
                    .max(Comparator.comparing(ItvEntity::getFechaApto))
                    .ifPresent(itv -> model.addAttribute("ultimaItv", itv));

            c.getMantenimientos().stream()
                    .max(Comparator.comparing(MantenimientoEntity::getFecha))
                    .ifPresent(m -> model.addAttribute("ultimoMantenimiento", m));

            double gastoRepostajes = c.getRepostajes() != null
                    ? c.getRepostajes().stream()
                    .mapToDouble(RepostajeEntity::getPrecio)
                    .sum()
                    : 0.0;
            model.addAttribute("gastoRepostajes", gastoRepostajes);
        });
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
