package com.mantenimiento.springItv.config;

import com.mantenimiento.springItv.dto.AlertaDto;
import com.mantenimiento.springItv.entities.UsuarioEntity;
import com.mantenimiento.springItv.secutity.CustomUserDetails;
import com.mantenimiento.springItv.services.AlertasService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AlertInterceptor implements HandlerInterceptor {

    private final AlertasService alertaService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("alertas") != null) {
            return true; // No sesión o ya están las alertas
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            UsuarioEntity usuario = userDetails.getUsuario();
            List<AlertaDto> alertas = alertaService.generarAlertasUsuario(usuario.getId());
            session.setAttribute("alertas", alertas);
        }

        return true;
    }
}
