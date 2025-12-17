package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.dto.CategoriaDto;
import com.mantenimiento.springItv.entities.CategoriaEntity;
import com.mantenimiento.springItv.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CategoriaRestController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDto>> listarCategorias(
            @RequestParam(required = false) Integer tipo) {
        List<CategoriaEntity> categorias = categoriaService.listarCategorias();
        
        // Filtrar por tipo si se proporciona
        if (tipo != null) {
            categorias = categorias.stream()
                    .filter(c -> c.getTipoCategoria() != null && c.getTipoCategoria().equals(tipo))
                    .collect(Collectors.toList());
        }
        
        List<CategoriaDto> respuesta = categorias.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(respuesta);
    }

    private CategoriaDto toDto(CategoriaEntity c) {
        CategoriaDto dto = new CategoriaDto();
        dto.setId(c.getIdCategoria());
        dto.setNombre(c.getDescripcion());
        dto.setTipo(c.getTipoCategoria());
        return dto;
    }
}

