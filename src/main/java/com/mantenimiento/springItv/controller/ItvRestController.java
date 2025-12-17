package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.dto.ItvDto;
import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.entities.ItvEntity;
import com.mantenimiento.springItv.entities.UsuarioEntity;
import com.mantenimiento.springItv.secutity.CustomUserDetails;
import com.mantenimiento.springItv.services.CocheService;
import com.mantenimiento.springItv.services.ItvService;
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
public class ItvRestController {

    @Autowired
    private ItvService itvService;

    @Autowired
    private CocheService cocheService;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/coches/{matricula}/itv")
    public ResponseEntity<List<ItvDto>> listarItvs(@PathVariable String matricula,
                                                   @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);

        if (cocheOpt.isEmpty() || !perteneceAlUsuario(cocheOpt.get(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        List<ItvEntity> lista = itvService.listarItvs(matricula);
        List<ItvDto> respuesta = lista.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/coches/{matricula}/itv")
    public ResponseEntity<ItvDto> crearItv(@PathVariable String matricula,
                                           @RequestBody ItvDto dto,
                                           @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);

        if (cocheOpt.isEmpty() || !perteneceAlUsuario(cocheOpt.get(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        CocheEntity coche = cocheOpt.get();

        ItvEntity itv = new ItvEntity();
        itv.setPrecio(dto.getPrecio());
        itv.setApto(dto.isApto());
        itv.setKmRevision(dto.getKmRevision());
        itv.setObservaciones(dto.getObservaciones());
        itv.setCoche(coche);

        try {
            if (dto.getFechaApto() != null) {
                Date fechaApto = DATE_FORMAT.parse(dto.getFechaApto());
                itv.setFechaApto(fechaApto);
            }
            if (dto.getFechaProximaItv() != null) {
                LocalDate prox = LocalDate.parse(dto.getFechaProximaItv());
                itv.setFechaProximaItv(prox);
            }
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

        ItvEntity guardada = itvService.guardarItv(itv);
        return ResponseEntity.ok(toDto(guardada));
    }

    @GetMapping("/itv/{id}")
    public ResponseEntity<ItvDto> obtenerItv(@PathVariable Integer id,
                                             @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<ItvEntity> itvOpt = itvService.obtenerPorId(id);

        if (itvOpt.isEmpty() || !perteneceAlUsuario(itvOpt.get().getCoche(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toDto(itvOpt.get()));
    }

    @DeleteMapping("/itv/{id}")
    public ResponseEntity<Void> eliminarItv(@PathVariable Integer id,
                                            @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<ItvEntity> itvOpt = itvService.obtenerPorId(id);

        if (itvOpt.isEmpty() || !perteneceAlUsuario(itvOpt.get().getCoche(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        itvService.eliminarItv(id);
        return ResponseEntity.noContent().build();
    }

    private boolean perteneceAlUsuario(CocheEntity coche, UsuarioEntity usuario) {
        return coche.getUsuario() != null && coche.getUsuario().getId().equals(usuario.getId());
    }

    private ItvDto toDto(ItvEntity itv) {
        ItvDto dto = new ItvDto();
        dto.setIdFactura(itv.getIdFactura());
        dto.setPrecio(itv.getPrecio());
        dto.setApto(itv.isApto());
        dto.setKmRevision(itv.getKmRevision());
        dto.setObservaciones(itv.getObservaciones());
        dto.setMatricula(itv.getCoche() != null ? itv.getCoche().getMatricula() : null);

        if (itv.getFechaApto() != null) {
            dto.setFechaApto(DATE_FORMAT.format(itv.getFechaApto()));
        }
        if (itv.getFechaProximaItv() != null) {
            dto.setFechaProximaItv(itv.getFechaProximaItv().toString());
        }

        return dto;
    }
}


