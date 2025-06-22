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
            session.setAttribute("usuario", usuario); // <-- aquí guardas en sesión
            return "redirect:/coches";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "usuario/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/usuarios/login";
    }


}
