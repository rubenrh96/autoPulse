package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.dto.CocheDto;
import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.entities.UsuarioEntity;
import com.mantenimiento.springItv.secutity.CustomUserDetails;
import com.mantenimiento.springItv.services.CocheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coches")
public class CocheRestController {

    @Autowired
    private CocheService cocheService;

    @GetMapping
    public ResponseEntity<List<CocheDto>> listarCoches(@AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        List<CocheEntity> coches = cocheService.listarCochesPorUsuario(usuario.getId());
        List<CocheDto> respuesta = coches.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<CocheDto> obtenerCoche(@PathVariable String matricula,
                                                 @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);

        if (cocheOpt.isEmpty() || !perteneceAlUsuario(cocheOpt.get(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toDto(cocheOpt.get()));
    }

    @PostMapping
    public ResponseEntity<CocheDto> crearCoche(@RequestBody CocheDto dto,
                                               @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();

        CocheEntity coche = new CocheEntity();
        coche.setMatricula(dto.getMatricula());
        coche.setMarca(dto.getMarca());
        coche.setModelo(dto.getModelo());
        coche.setColor(dto.getColor());
        coche.setCv(dto.getCv());
        coche.setAno(dto.getAno());
        coche.setKilometros(dto.getKilometros());
        coche.setUsuario(usuario);

        CocheEntity guardado = cocheService.guardarCoche(coche);
        return ResponseEntity.ok(toDto(guardado));
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<CocheDto> actualizarCoche(@PathVariable String matricula,
                                                    @RequestBody CocheDto dto,
                                                    @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);

        if (cocheOpt.isEmpty() || !perteneceAlUsuario(cocheOpt.get(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        CocheEntity coche = cocheOpt.get();
        coche.setMarca(dto.getMarca());
        coche.setModelo(dto.getModelo());
        coche.setColor(dto.getColor());
        coche.setCv(dto.getCv());
        coche.setAno(dto.getAno());
        coche.setKilometros(dto.getKilometros());

        CocheEntity actualizado = cocheService.guardarCoche(coche);
        return ResponseEntity.ok(toDto(actualizado));
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> eliminarCoche(@PathVariable String matricula,
                                              @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);

        if (cocheOpt.isEmpty() || !perteneceAlUsuario(cocheOpt.get(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        cocheService.eliminarCoche(matricula);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{matricula}/kilometros")
    public ResponseEntity<CocheDto> actualizarKilometros(@PathVariable String matricula,
                                                         @RequestParam int kilometros,
                                                         @AuthenticationPrincipal CustomUserDetails user) {
        UsuarioEntity usuario = user.getUsuario();
        Optional<CocheEntity> cocheOpt = cocheService.obtenerPorId(matricula);

        if (cocheOpt.isEmpty() || !perteneceAlUsuario(cocheOpt.get(), usuario)) {
            return ResponseEntity.notFound().build();
        }

        CocheEntity coche = cocheOpt.get();
        coche.setKilometros(kilometros);
        CocheEntity actualizado = cocheService.guardarCoche(coche);

        return ResponseEntity.ok(toDto(actualizado));
    }

    private boolean perteneceAlUsuario(CocheEntity coche, UsuarioEntity usuario) {
        return coche.getUsuario() != null && coche.getUsuario().getId().equals(usuario.getId());
    }

    private CocheDto toDto(CocheEntity coche) {
        CocheDto dto = new CocheDto();
        dto.setMatricula(coche.getMatricula());
        dto.setMarca(coche.getMarca());
        dto.setModelo(coche.getModelo());
        dto.setColor(coche.getColor());
        dto.setCv(coche.getCv());
        dto.setAno(coche.getAno());
        dto.setKilometros(coche.getKilometros());
        return dto;
    }
}


