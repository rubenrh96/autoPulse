package com.mantenimiento.springItv.controller;

import javax.validation.Valid;
import com.mantenimiento.springItv.dto.SeguroDto;
import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.entities.SeguroEntity;
import com.mantenimiento.springItv.entities.UsuarioEntity;
import com.mantenimiento.springItv.secutity.CustomUserDetails;
import com.mantenimiento.springItv.services.CocheService;
import com.mantenimiento.springItv.services.SeguroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SeguroRestController {

    @Autowired
    private SeguroService seguroService;

    @Autowired
    private CocheService cocheService;

    private SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    @GetMapping("/coches/{matricula}/seguro")
    public ResponseEntity<List<SeguroDto>> listarSeguros(@PathVariable String matricula,
                                                          @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);

        if (cocheOpt.isEmpty() || !perteneceAlUsuario(cocheOpt.get(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        List<SeguroEntity> lista = seguroService.listarSeguros(matricula);
        List<SeguroDto> respuesta = lista.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/coches/{matricula}/seguro")
    public ResponseEntity<SeguroDto> crearSeguro(@PathVariable String matricula,
                                                 @Valid @RequestBody SeguroDto dto,
                                                 @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);

        if (cocheOpt.isEmpty() || !perteneceAlUsuario(cocheOpt.get(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        CocheEntity coche = cocheOpt.get();

        SeguroEntity seguro = new SeguroEntity();
        seguro.setPrecio(dto.getPrecio());
        seguro.setCompania(dto.getCompania());
        seguro.setObservaciones(dto.getObservaciones());
        seguro.setCoche(coche);

        try {
            if (dto.getFechaInicio() != null) {
                Date fechaInicio = dateFormat().parse(dto.getFechaInicio());
                seguro.setFechaInicio(fechaInicio);
            }
            if (dto.getFechaRenovacion() != null) {
                LocalDate renovacion = LocalDate.parse(dto.getFechaRenovacion());
                seguro.setFechaRenovacion(renovacion);
            }
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

        SeguroEntity guardado = seguroService.guardarSeguro(seguro);
        return ResponseEntity.ok(toDto(guardado));
    }

    @GetMapping("/seguro/{id}")
    public ResponseEntity<SeguroDto> obtenerSeguro(@PathVariable Integer id,
                                                    @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<SeguroEntity> seguroOpt = seguroService.obtenerPorId(id);

        if (seguroOpt.isEmpty() || !perteneceAlUsuario(seguroOpt.get().getCoche(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toDto(seguroOpt.get()));
    }

    @PutMapping("/seguro/{id}")
    public ResponseEntity<SeguroDto> actualizarSeguro(@PathVariable Integer id,
                                                       @Valid @RequestBody SeguroDto dto,
                                                       @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<SeguroEntity> seguroOpt = seguroService.obtenerPorId(id);

        if (seguroOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SeguroEntity seguro = seguroOpt.get();

        if (!perteneceAlUsuario(seguro.getCoche(), usuario)) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.FORBIDDEN).build();
        }

        seguro.setPrecio(dto.getPrecio());
        seguro.setCompania(dto.getCompania());
        seguro.setObservaciones(dto.getObservaciones());

        try {
            if (dto.getFechaInicio() != null) {
                Date fechaInicio = dateFormat().parse(dto.getFechaInicio());
                seguro.setFechaInicio(fechaInicio);
            }
            if (dto.getFechaRenovacion() != null) {
                LocalDate renovacion = LocalDate.parse(dto.getFechaRenovacion());
                seguro.setFechaRenovacion(renovacion);
            }
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

        SeguroEntity actualizado = seguroService.guardarSeguro(seguro);
        return ResponseEntity.ok(toDto(actualizado));
    }

    @DeleteMapping("/seguro/{id}")
    public ResponseEntity<Void> eliminarSeguro(@PathVariable Integer id,
                                               @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<SeguroEntity> seguroOpt = seguroService.obtenerPorId(id);

        if (seguroOpt.isEmpty() || !perteneceAlUsuario(seguroOpt.get().getCoche(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        seguroService.eliminarSeguro(id);
        return ResponseEntity.noContent().build();
    }

    private boolean perteneceAlUsuario(CocheEntity coche, UsuarioEntity usuario) {
        return coche.getUsuario() != null && coche.getUsuario().getId().equals(usuario.getId());
    }

    private SeguroDto toDto(SeguroEntity seguro) {
        SeguroDto dto = new SeguroDto();
        dto.setIdSeguro(seguro.getIdSeguro());
        dto.setPrecio(seguro.getPrecio());
        dto.setCompania(seguro.getCompania());
        dto.setObservaciones(seguro.getObservaciones());
        dto.setMatricula(seguro.getCoche() != null ? seguro.getCoche().getMatricula() : null);

        if (seguro.getFechaInicio() != null) {
            dto.setFechaInicio(dateFormat().format(seguro.getFechaInicio()));
        }
        if (seguro.getFechaRenovacion() != null) {
            dto.setFechaRenovacion(seguro.getFechaRenovacion().toString());
        }

        return dto;
    }
}
