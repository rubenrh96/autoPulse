package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.exception.KilometrajeInvalidoException;
import com.mantenimiento.springItv.entities.RepostajeEntity;
import com.mantenimiento.springItv.models.Repostaje;
import com.mantenimiento.springItv.services.CocheService;
import com.mantenimiento.springItv.services.RepostajeService;
import com.mantenimiento.springItv.transformadores.TransformadorRepostaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/repostajes")
public class RepostajeController {

    @Autowired
    private RepostajeService repostajeService;

    @Autowired
    private CocheService cocheService;

    @GetMapping("/{matricula}")
    public String mostrarFormularioDeAlta(@PathVariable("matricula") String matricula, Model model) {
        if (!model.containsAttribute("repostaje")) {
            model.addAttribute("repostaje", new RepostajeEntity());
        };
        model.addAttribute("matricula", matricula);
        return "repostaje/agregarRepostaje";
    }

    @PostMapping("/{matricula}")
    public String crearRepostaje(RepostajeEntity repostaje, @RequestParam("matricula") String matricula, RedirectAttributes attrs) {
        try{
            repostajeService.guardarRepostaje(repostaje, matricula);
        }catch (KilometrajeInvalidoException e){
            attrs.addFlashAttribute("repostaje", repostaje);
            return "redirect:/repostajes/" + matricula + "?error=repostaje";
        }
        return "redirect:/coches/" + matricula + "?success=repostaje";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarRepostaje(@PathVariable("id") Integer id) {
        repostajeService.eliminarRepostaje(id);
        return "redirect:/coches";
    }

    @GetMapping("/visualizar/{idRepostaje}")
    public String buscarRepostajePorId(@PathVariable("idRepostaje") Integer id, Model model) {
        repostajeService.obtenerPorId(id).ifPresent(repostaje -> {
            model.addAttribute("repostaje", repostaje);
            model.addAttribute("matricula", repostaje.getCoche().getMatricula());
        });
        return "repostaje/visualizarRepostaje";
    }

    @GetMapping("/lista/{matricula}")
    public String listarRepostajes(@PathVariable("matricula") String matricula, Model model) {
        List<Repostaje> lista = repostajeService.listarRepostajes(matricula).stream()
                .map(TransformadorRepostaje::repostajeEntityToRepostaje)
                .sorted(Comparator.comparing(Repostaje::getFecha).reversed())
                .collect(Collectors.toList());
        model.addAttribute("listaRepostajes", lista);
        model.addAttribute("matricula", matricula);
        return "repostaje/listaRepostaje";
    }
}