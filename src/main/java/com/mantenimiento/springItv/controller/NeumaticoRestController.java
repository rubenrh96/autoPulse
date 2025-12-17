package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.dto.NeumaticoDto;
import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.entities.NeumaticoEntity;
import com.mantenimiento.springItv.entities.UsuarioEntity;
import com.mantenimiento.springItv.secutity.CustomUserDetails;
import com.mantenimiento.springItv.services.CocheService;
import com.mantenimiento.springItv.services.NeumaticoService;
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
public class NeumaticoRestController {

    @Autowired
    private NeumaticoService neumaticoService;

    @Autowired
    private CocheService cocheService;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/coches/{matricula}/neumaticos")
    public ResponseEntity<List<NeumaticoDto>> listarNeumaticos(@PathVariable String matricula,
                                                               @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);

        if (cocheOpt.isEmpty() || !perteneceAlUsuario(cocheOpt.get(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        List<NeumaticoDto> lista = neumaticoService.listarNeumaticos(matricula).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/coches/{matricula}/neumaticos")
    public ResponseEntity<NeumaticoDto> crearNeumatico(@PathVariable String matricula,
                                                       @RequestBody NeumaticoDto dto,
                                                       @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);

        if (cocheOpt.isEmpty() || !perteneceAlUsuario(cocheOpt.get(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        CocheEntity coche = cocheOpt.get();

        NeumaticoEntity n = new NeumaticoEntity();
        n.setMarca(dto.getMarca());
        n.setModelo(dto.getModelo());
        n.setAnchoLlanta(dto.getAnchoLlanta());
        n.setPerfilLlanta(dto.getPerfilLlanta());
        n.setDiametroLlanta(dto.getDiametroLlanta());
        n.setIndiceCarga(dto.getIndiceCarga());
        n.setIndiceVelocidad(dto.getIndiceVelocidad());
        n.setPrecio(dto.getPrecio());
        n.setKmMontaje(dto.getKmMontaje());
        n.setDescripcion(dto.getDescripcion());
        n.setMs(dto.isMs());
        n.setNumero(dto.getNumero());
        n.setCoche(coche);

        try {
            if (dto.getFechaMontaje() != null) {
                Date fecha = DATE_FORMAT.parse(dto.getFechaMontaje());
                n.setFechaMontaje(fecha);
            }
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

        NeumaticoEntity guardado = neumaticoService.guardarNeumatico(n);
        return ResponseEntity.ok(toDto(guardado));
    }

    @GetMapping("/neumaticos/{id}")
    public ResponseEntity<NeumaticoDto> obtenerNeumatico(@PathVariable Integer id,
                                                         @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<NeumaticoEntity> nOpt = neumaticoService.listarTodos().stream()
                .filter(n -> n.getIdNeumatico() == id)
                .findFirst();

        if (nOpt.isEmpty() || !perteneceAlUsuario(nOpt.get().getCoche(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toDto(nOpt.get()));
    }

    @DeleteMapping("/neumaticos/{id}")
    public ResponseEntity<Void> eliminarNeumatico(@PathVariable Integer id,
                                                  @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<NeumaticoEntity> nOpt = neumaticoService.listarTodos().stream()
                .filter(n -> n.getIdNeumatico() == id)
                .findFirst();

        if (nOpt.isEmpty() || !perteneceAlUsuario(nOpt.get().getCoche(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        neumaticoService.eliminarNeumatico(id);
        return ResponseEntity.noContent().build();
    }

    private boolean perteneceAlUsuario(CocheEntity coche, UsuarioEntity usuario) {
        return coche.getUsuario() != null && coche.getUsuario().getId().equals(usuario.getId());
    }

    private NeumaticoDto toDto(NeumaticoEntity n) {
        NeumaticoDto dto = new NeumaticoDto();
        dto.setIdNeumatico(n.getIdNeumatico());
        dto.setMarca(n.getMarca());
        dto.setModelo(n.getModelo());
        dto.setAnchoLlanta(n.getAnchoLlanta());
        dto.setPerfilLlanta(n.getPerfilLlanta());
        dto.setDiametroLlanta(n.getDiametroLlanta());
        dto.setIndiceCarga(n.getIndiceCarga());
        dto.setIndiceVelocidad(n.getIndiceVelocidad());
        dto.setPrecio(n.getPrecio());
        dto.setKmMontaje(n.getKmMontaje());
        dto.setDescripcion(n.getDescripcion());
        dto.setMs(n.isMs());
        dto.setNumero(n.getNumero());
        dto.setMatricula(n.getCoche() != null ? n.getCoche().getMatricula() : null);

        if (n.getFechaMontaje() != null) {
            dto.setFechaMontaje(DATE_FORMAT.format(n.getFechaMontaje()));
        }

        return dto;
    }
}


