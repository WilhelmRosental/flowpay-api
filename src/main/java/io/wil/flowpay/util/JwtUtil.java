package io.wil.flowpay.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "default-secret-key"; // Remplacez par une clé secrète sécurisée
    private final long EXPIRATION_TIME = 86400000; // 1 jour en millisecondes

    /**
     * Génère un token JWT à partir d'un userId de type Long.
     */
    public String generateToken(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Surcharge : génère un token à partir d'un userId déjà sous forme de String.
     */
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId) // Utilise directement le String sans conversion
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Extrait l'ID de l'utilisateur (en String) depuis le token.
     */
    public String extractUserId(String token) throws Exception {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            throw new Exception("Invalid or expired token", e);
        }
    }

    /**
     * Vérifie si un token est valide (non-expiré et signé correctement).
     */
    public boolean isTokenValid(String token) {
        try {
            extractUserId(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

