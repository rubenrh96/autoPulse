package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.entities.UsuarioEntity;
import com.mantenimiento.springItv.repositories.UsuarioRepository;
import com.mantenimiento.springItv.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
        model.addAttribute("usuario", new UsuarioEntity());   // ← usa la entidad
        return "registro/registro";
    }

    @PostMapping("/registro")
    public String doRegistro(@ModelAttribute("usuario") UsuarioEntity usuario) {
        usuarioService.registrar(usuario);
        return "redirect:/usuarios/login";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "registro/login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                Model model,
                                HttpSession session) {
        UsuarioEntity usuario = usuarioRepository.findByUsername(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            session.setAttribute("usuario", usuario);
            return "redirect:/coches";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "registro/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/usuarios/login";
    }

    @GetMapping("/ajustes")
    public String mostrarAjustes(HttpSession session, Model model) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
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
                                  HttpSession session,
                                  Model model) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        if (usuario == null) {
            return "redirect:/usuarios/login";
        }
        if (!nueva.equals(confirmar)) {
            model.addAttribute("error", "Las contraseñas nuevas no coinciden");
            return "ajustes";
        }
        if (!usuario.getPassword().equals(actual)) {
            model.addAttribute("error", "La contraseña actual no es correcta");
            return "ajustes";
        }
        usuario.setPassword(nueva);
        usuarioRepository.save(usuario);
        model.addAttribute("mensaje", "Contraseña actualizada correctamente");
        return "ajustes";
    }

    @PostMapping("/eliminar")
    public String eliminarCuenta(HttpSession session) {
        UsuarioEntity usuario = (UsuarioEntity) session.getAttribute("usuario");
        if (usuario != null) {
            usuarioRepository.deleteById(usuario.getId());
            session.invalidate();
        }
        return "redirect:/usuarios/login";
    }



}
