package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.entities.UsuarioEntity;
import com.mantenimiento.springItv.repositories.UsuarioRepository;
import com.mantenimiento.springItv.secutity.CustomUserDetails;
import com.mantenimiento.springItv.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private final UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String showRegistro(Model model) {
        model.addAttribute("usuario", new UsuarioEntity());
        return "registro/registro";
    }

    @PostMapping("/registro")
    public String doRegistro(@ModelAttribute("usuario") UsuarioEntity usuario) {
        usuarioService.registrar(usuario);
        return "redirect:/usuarios/login" + "?success=usuario";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "registro/login"; // Solo devuelve la vista
    }

    @GetMapping("/ajustes")
    public String mostrarAjustes(   Model model) {
        UsuarioEntity usuario = getUsuarioActual();
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }
        model.addAttribute("usuario", usuario);
        return "config/ajustes";
    }

    @PostMapping("/cambiar-password")
    public String cambiarPassword(@RequestParam String actual,
                                  @RequestParam String nueva,
                                  @RequestParam String confirmar,
                                  Model model) {
        UsuarioEntity usuario = getUsuarioActual();
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }

        if (!nueva.equals(confirmar)) {
            model.addAttribute("error", "Las contraseñas nuevas no coinciden");
            return "config/ajustes";
        }

        if (!usuario.getPassword().equals(actual)) {
            model.addAttribute("error", "La contraseña actual no es correcta");
            return "config/ajustes";
        }

        usuario.setPassword(nueva);
        usuarioRepository.save(usuario);
        model.addAttribute("mensaje", "Contraseña actualizada correctamente");
        return "config/ajustes";
    }

    @PostMapping("/eliminar")
    public String eliminarCuenta() {
        UsuarioEntity usuario = getUsuarioActual();
        if (usuario != null) {
            usuarioRepository.deleteById(usuario.getId());
        }
        return "redirect:/usuarios/login?deleted";
    }

    private UsuarioEntity getUsuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) auth.getPrincipal()).getUsuario();
        }
        return null;
    }
}
