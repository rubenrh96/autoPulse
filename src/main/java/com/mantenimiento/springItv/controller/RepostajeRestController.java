package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.dto.RepostajeDto;
import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.entities.RepostajeEntity;
import com.mantenimiento.springItv.entities.UsuarioEntity;
import com.mantenimiento.springItv.exception.KilometrajeInvalidoException;
import com.mantenimiento.springItv.secutity.CustomUserDetails;
import com.mantenimiento.springItv.services.CocheService;
import com.mantenimiento.springItv.services.RepostajeService;
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
public class RepostajeRestController {

    @Autowired
    private RepostajeService repostajeService;

    @Autowired
    private CocheService cocheService;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/coches/{matricula}/repostajes")
    public ResponseEntity<List<RepostajeDto>> listarRepostajes(@PathVariable String matricula,
                                                               @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);

        if (cocheOpt.isEmpty() || !perteneceAlUsuario(cocheOpt.get(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        List<RepostajeDto> lista = repostajeService.listarRepostajes(matricula).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/coches/{matricula}/repostajes")
    public ResponseEntity<?> crearRepostaje(@PathVariable String matricula,
                                            @RequestBody RepostajeDto dto,
                                            @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);

        if (cocheOpt.isEmpty() || !perteneceAlUsuario(cocheOpt.get(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        RepostajeEntity repostaje = new RepostajeEntity();
        repostaje.setPrecio(dto.getPrecio());
        repostaje.setLitros(dto.getLitros());
        repostaje.setPrecioLitro(dto.getPrecioLitro());
        repostaje.setKmRepostaje(dto.getKmRepostaje());

        try {
            if (dto.getFecha() != null) {
                Date fecha = DATE_FORMAT.parse(dto.getFecha());
                repostaje.setFecha(fecha);
            }
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Fecha con formato inválido (usar yyyy-MM-dd)");
        }

        try {
            repostajeService.guardarRepostaje(repostaje, matricula);
        } catch (KilometrajeInvalidoException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.ok(toDto(repostaje));
    }

    @GetMapping("/repostajes/{id}")
    public ResponseEntity<RepostajeDto> obtenerRepostaje(@PathVariable Integer id,
                                                         @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<RepostajeEntity> repostajeOpt = repostajeService.obtenerPorId(id);

        if (repostajeOpt.isEmpty() || !perteneceAlUsuario(repostajeOpt.get().getCoche(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toDto(repostajeOpt.get()));
    }

    @DeleteMapping("/repostajes/{id}")
    public ResponseEntity<Void> eliminarRepostaje(@PathVariable Integer id,
                                                  @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<RepostajeEntity> repostajeOpt = repostajeService.obtenerPorId(id);

        if (repostajeOpt.isEmpty() || !perteneceAlUsuario(repostajeOpt.get().getCoche(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        repostajeService.eliminarRepostaje(id);
        return ResponseEntity.noContent().build();
    }

    private boolean perteneceAlUsuario(CocheEntity coche, UsuarioEntity usuario) {
        return coche.getUsuario() != null && coche.getUsuario().getId().equals(usuario.getId());
    }

    private RepostajeDto toDto(RepostajeEntity r) {
        RepostajeDto dto = new RepostajeDto();
        dto.setIdRepostaje(r.getIdRepostaje());
        dto.setPrecio(r.getPrecio());
        dto.setLitros(r.getLitros());
        dto.setPrecioLitro(r.getPrecioLitro());
        dto.setKmRepostaje(r.getKmRepostaje());
        dto.setMatricula(r.getCoche() != null ? r.getCoche().getMatricula() : null);

        if (r.getFecha() != null) {
            dto.setFecha(DATE_FORMAT.format(r.getFecha()));
        }

        return dto;
    }
}


