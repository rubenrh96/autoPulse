package com.mantenimiento.springItv.secutity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String JWT_SECRET = "cambio-esta-clave-por-una-mas-segura";
    private static final long JWT_EXPIRATION_MS = 1000 * 60 * 60 * 24; // 24 horas

    public String generarToken(String username) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String obtenerUsernameDeToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}



