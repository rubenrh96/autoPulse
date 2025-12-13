package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.secutity.CustomUserDetails;
import com.mantenimiento.springItv.services.ItvService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/itv")
public class CalendarController {

    private final ItvService itvService;

    public CalendarController(ItvService itvService) {
        this.itvService = itvService;
    }

    @GetMapping("/proximas")
    public List<Map<String, Object>> proximasItvs(@AuthenticationPrincipal CustomUserDetails user) {

        List<LocalDate> lista = itvService.listarItvPorUsuario(user.getUsuario().getId());

        return lista.stream().map(fecha -> {
            Map<String, Object> ev = new HashMap<>();
            ev.put("title", " ITV");
            ev.put("start", fecha);
            ev.put("allDay", true);
            ev.put("backgroundColor", "#ff9f43");
            return ev;
        }).toList();
    }
}

