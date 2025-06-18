package com.mantenimiento.springItv.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mantenimiento.springItv.entities.CategoriaEntity;
import com.mantenimiento.springItv.entities.RecambioEntity;
import com.mantenimiento.springItv.models.Recambio;
import com.mantenimiento.springItv.services.CategoriaService;
import com.mantenimiento.springItv.services.RecambioService;

@Controller
public class RecambioController {

    @Autowired
    private RecambioService recambioService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/recambios")
    public String mostrarFormularioDeAltaRecambio(Model model) {
        List<CategoriaEntity> categoriasRecambio = new ArrayList<>();
        List<CategoriaEntity> categorias = categoriaService.listarCategorias();
        for (CategoriaEntity categoriaEntity : categorias) {
            if (categoriaEntity.getTipoCategoria().equals(2)) {
                categoriasRecambio.add(categoriaEntity);
            }
        }
        model.addAttribute("recambio", new Recambio());
        model.addAttribute("categorias", categoriasRecambio);
        return "recambio/agregarRecambio";
    }

    @PostMapping("/recambios")
    public String crearRecambio(RecambioEntity recambio) {
        recambioService.guardarRecambio(recambio);
        return "redirect:/coches";
    }
}
