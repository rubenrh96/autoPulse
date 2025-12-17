package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.dto.RecambioDto;
import com.mantenimiento.springItv.entities.CategoriaEntity;
import com.mantenimiento.springItv.entities.RecambioEntity;
import com.mantenimiento.springItv.entities.UsuarioEntity;
import com.mantenimiento.springItv.secutity.CustomUserDetails;
import com.mantenimiento.springItv.services.CategoriaService;
import com.mantenimiento.springItv.services.RecambioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recambios")
public class RecambioRestController {

    @Autowired
    private RecambioService recambioService;

    @Autowired
    private CategoriaService categoriaService;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping
    public ResponseEntity<List<RecambioDto>> listarRecambios(@AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        List<RecambioDto> lista = recambioService.listarRecambiosPorUsuario(usuario.getId()).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<RecambioDto> crearRecambio(@RequestBody RecambioDto dto,
                                                     @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();

        RecambioEntity r = new RecambioEntity();
        r.setDescripcion(dto.getDescripcion());
        r.setPrecio(dto.getPrecio());
        r.setCantidad(dto.getCantidad());
        r.setUsuario(usuario);

        if (dto.getIdCategoria() != null) {
            Optional<CategoriaEntity> categoriaOpt = categoriaService.listarCategorias().stream()
                    .filter(c -> c.getIdCategoria().equals(dto.getIdCategoria()))
                    .findFirst();
            categoriaOpt.ifPresent(r::setCategoria);
        }

        try {
            if (dto.getFechaCompra() != null) {
                Date fecha = DATE_FORMAT.parse(dto.getFechaCompra());
                r.setFechaCompra(fecha);
            }
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

        RecambioEntity guardado = recambioService.guardarRecambio(r);
        return ResponseEntity.ok(toDto(guardado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecambioDto> obtenerRecambio(@PathVariable Integer id,
                                                       @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<RecambioEntity> rOpt = recambioService.obtenerPorId(id);

        if (rOpt.isEmpty() || !rOpt.get().getUsuario().getId().equals(usuario.getId())) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toDto(rOpt.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecambio(@PathVariable Integer id,
                                                 @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<RecambioEntity> rOpt = recambioService.obtenerPorId(id);

        if (rOpt.isEmpty() || !rOpt.get().getUsuario().getId().equals(usuario.getId())) {
            return ResponseEntity.notFound().build();
        }

        recambioService.eliminarRecambio(id);
        return ResponseEntity.noContent().build();
    }

    private RecambioDto toDto(RecambioEntity r) {
        RecambioDto dto = new RecambioDto();
        dto.setIdRecambio(r.getIdRecambio());
        dto.setDescripcion(r.getDescripcion());
        dto.setPrecio(r.getPrecio());
        dto.setCantidad(r.getCantidad());

        if (r.getFechaCompra() != null) {
            dto.setFechaCompra(DATE_FORMAT.format(r.getFechaCompra()));
        }

        if (r.getCategoria() != null) {
            dto.setIdCategoria(r.getCategoria().getIdCategoria());
            dto.setCategoria(r.getCategoria().getDescripcion());
        }

        return dto;
    }
}


