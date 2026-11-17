package com.example.demo;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final SecretKey key = Keys.hmacShaKeyFor(
        "MySecretKeyForBankingApplication123456"
        .getBytes()
    );

    public String generateToken(String username) {

        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration(
                new Date(System.currentTimeMillis() + 3600000)
            )
            .signWith(key)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}