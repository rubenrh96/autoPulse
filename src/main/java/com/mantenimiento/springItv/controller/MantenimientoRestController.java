package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.dto.MantenimientoDto;
import com.mantenimiento.springItv.entities.CategoriaEntity;
import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.entities.MantenimientoEntity;
import com.mantenimiento.springItv.entities.UsuarioEntity;
import com.mantenimiento.springItv.secutity.CustomUserDetails;
import com.mantenimiento.springItv.services.CategoriaService;
import com.mantenimiento.springItv.services.CocheService;
import com.mantenimiento.springItv.services.MantenimientoService;
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
@RequestMapping("/api")
public class MantenimientoRestController {

    @Autowired
    private MantenimientoService mantenimientoService;

    @Autowired
    private CocheService cocheService;

    @Autowired
    private CategoriaService categoriaService;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/coches/{matricula}/mantenimientos")
    public ResponseEntity<List<MantenimientoDto>> listarMantenimientos(@PathVariable String matricula,
                                                                       @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);

        if (cocheOpt.isEmpty() || !perteneceAlUsuario(cocheOpt.get(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        List<MantenimientoEntity> lista = mantenimientoService.listarMantenimientos(matricula);
        List<MantenimientoDto> respuesta = lista.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/coches/{matricula}/mantenimientos")
    public ResponseEntity<MantenimientoDto> crearMantenimiento(@PathVariable String matricula,
                                                               @RequestBody MantenimientoDto dto,
                                                               @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);

        if (cocheOpt.isEmpty() || !perteneceAlUsuario(cocheOpt.get(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        CocheEntity coche = cocheOpt.get();

        MantenimientoEntity mantenimiento = new MantenimientoEntity();
        mantenimiento.setDescripcion(dto.getDescripcion());
        mantenimiento.setPrecio(dto.getPrecio());
        mantenimiento.setKmMantenimiento(dto.getKmMantenimiento());
        mantenimiento.setPagado(dto.isPagado());
        mantenimiento.setCoche(coche);

        if (dto.getIdCategoria() != null) {
            Optional<CategoriaEntity> categoriaOpt = categoriaService.listarCategorias().stream()
                    .filter(c -> c.getIdCategoria().equals(dto.getIdCategoria()))
                    .findFirst();
            categoriaOpt.ifPresent(mantenimiento::setCategoria);
        }

        try {
            if (dto.getFecha() != null) {
                Date fecha = DATE_FORMAT.parse(dto.getFecha());
                mantenimiento.setFecha(fecha);
            }
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

        MantenimientoEntity guardado = mantenimientoService.guardarMantenimiento(mantenimiento);
        return ResponseEntity.ok(toDto(guardado));
    }

    @GetMapping("/mantenimientos/{id}")
    public ResponseEntity<MantenimientoDto> obtenerMantenimiento(@PathVariable Integer id,
                                                                 @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<MantenimientoEntity> mantOpt = mantenimientoService.obtenerPorId(id);

        if (mantOpt.isEmpty() || !perteneceAlUsuario(mantOpt.get().getCoche(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toDto(mantOpt.get()));
    }

    @PutMapping("/mantenimientos/{id}")
    public ResponseEntity<MantenimientoDto> actualizarMantenimiento(@PathVariable Integer id,
                                                                     @RequestBody MantenimientoDto dto,
                                                                     @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<MantenimientoEntity> mantOpt = mantenimientoService.obtenerPorId(id);

        if (mantOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MantenimientoEntity mantenimiento = mantOpt.get();

        // Verificar permisos (a través del coche)
        if (!perteneceAlUsuario(mantenimiento.getCoche(), usuario)) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build();
        }

        // Actualizar campos
        mantenimiento.setDescripcion(dto.getDescripcion());
        mantenimiento.setPrecio(dto.getPrecio());
        mantenimiento.setKmMantenimiento(dto.getKmMantenimiento());
        mantenimiento.setPagado(dto.isPagado());

        // Actualizar categoría si viene en el DTO
        if (dto.getIdCategoria() != null) {
            Optional<CategoriaEntity> categoriaOpt = categoriaService.listarCategorias().stream()
                    .filter(c -> c.getIdCategoria().equals(dto.getIdCategoria()))
                    .findFirst();
            categoriaOpt.ifPresent(mantenimiento::setCategoria);
        }

        try {
            if (dto.getFecha() != null) {
                Date fecha = DATE_FORMAT.parse(dto.getFecha());
                mantenimiento.setFecha(fecha);
            }
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

        MantenimientoEntity actualizado = mantenimientoService.guardarMantenimiento(mantenimiento);
        return ResponseEntity.ok(toDto(actualizado));
    }

    @DeleteMapping("/mantenimientos/{id}")
    public ResponseEntity<Void> eliminarMantenimiento(@PathVariable Integer id,
                                                      @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<MantenimientoEntity> mantOpt = mantenimientoService.obtenerPorId(id);

        if (mantOpt.isEmpty() || !perteneceAlUsuario(mantOpt.get().getCoche(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        mantenimientoService.eliminarMantenimiento(id);
        return ResponseEntity.noContent().build();
    }

    private boolean perteneceAlUsuario(CocheEntity coche, UsuarioEntity usuario) {
        return coche.getUsuario() != null && coche.getUsuario().getId().equals(usuario.getId());
    }

    private MantenimientoDto toDto(MantenimientoEntity m) {
        MantenimientoDto dto = new MantenimientoDto();
        dto.setIdFactura(m.getIdFactura());
        dto.setDescripcion(m.getDescripcion());
        dto.setPrecio(m.getPrecio());
        dto.setKmMantenimiento(m.getKmMantenimiento());
        dto.setPagado(m.isPagado());
        dto.setMatricula(m.getCoche() != null ? m.getCoche().getMatricula() : null);

        if (m.getFecha() != null) {
            dto.setFecha(DATE_FORMAT.format(m.getFecha()));
        }

        if (m.getCategoria() != null) {
            dto.setIdCategoria(m.getCategoria().getIdCategoria());
            dto.setCategoria(m.getCategoria().getDescripcion());
        }

        return dto;
    }
}



