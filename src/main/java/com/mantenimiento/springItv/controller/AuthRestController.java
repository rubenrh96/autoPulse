package com.mantenimiento.springItv.controller;

import com.mantenimiento.springItv.dto.*;
import com.mantenimiento.springItv.entities.UsuarioEntity;
import com.mantenimiento.springItv.repositories.UsuarioRepository;
import com.mantenimiento.springItv.secutity.CustomUserDetails;
import com.mantenimiento.springItv.secutity.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Usamos el mismo encoder que en la configuración (actualmente NoOpPasswordEncoder)
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        String usernameOrEmail = loginRequest.getUsernameOrEmail();

        // Permitimos login por username o por email
        UsuarioEntity usuario = usuarioRepository.findByUsername(usernameOrEmail);
        if (usuario == null) {
            usuario = usuarioRepository.findByEmail(usernameOrEmail);
        }

        if (usuario == null) {
            return ResponseEntity.status(401).build();
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuario.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generarToken(usuario.getUsername());

        UsuarioDto usuarioDto = new UsuarioDto(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getEmail()
        );

        AuthResponseDto response = new AuthResponseDto(token, "Bearer", usuarioDto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDto> register(@RequestBody RegisterRequestDto request) {
        // Podrías añadir validaciones extra (existencia de username/email, etc.)
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername(request.getUsername());
        usuario.setNombre(request.getNombre());
        usuario.setApellidos(request.getApellidos());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        UsuarioEntity guardado = usuarioRepository.save(usuario);

        UsuarioDto usuarioDto = new UsuarioDto(
                guardado.getId(),
                guardado.getUsername(),
                guardado.getNombre(),
                guardado.getApellidos(),
                guardado.getEmail()
        );

        return ResponseEntity.ok(usuarioDto);
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioDto> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return ResponseEntity.status(401).build();
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UsuarioEntity usuario = userDetails.getUsuario();

        UsuarioDto usuarioDto = new UsuarioDto(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getEmail()
        );

        return ResponseEntity.ok(usuarioDto);
    }
}


