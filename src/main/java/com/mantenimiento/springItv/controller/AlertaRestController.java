package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.dto.AlertaVehiculoDto;
import com.mantenimiento.springItv.entities.CocheEntity;
import com.mantenimiento.springItv.secutity.CustomUserDetails;
import com.mantenimiento.springItv.services.AlertasService;
import com.mantenimiento.springItv.services.CocheService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
public class AlertaRestController {

    private final AlertasService alertasService;
    private final CocheService cocheService;

    /**
     * GET /api/alertas                      -> ITV, seguro y aceites de todos los coches, con urgencia ok|warning|danger
     * GET /api/alertas?soloUrgentes=true    -> solo warning/danger
     * GET /api/alertas?matricula=1234ABC    -> solo ese coche (404 si no es del usuario)
     */
    @GetMapping
    public ResponseEntity<List<AlertaVehiculoDto>> listar(
            @RequestParam(required = false) String matricula,
            @RequestParam(defaultValue = "false") boolean soloUrgentes,
            @AuthenticationPrincipal CustomUserDetails user) {

        Long usuarioId = user.getUsuario().getId();
        if (matricula == null || matricula.isBlank()) {
            return ResponseEntity.ok(alertasService.alertasDeUsuario(usuarioId, soloUrgentes));
        }
        Optional<CocheEntity> coche = cocheService.obtenerPorId(matricula);
        if (coche.isEmpty() || coche.get().getUsuario() == null
                || !coche.get().getUsuario().getId().equals(usuarioId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(alertasService.alertasDeCoche(coche.get(), soloUrgentes));
    }
}
