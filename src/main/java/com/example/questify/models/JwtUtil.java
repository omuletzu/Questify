package com.example.questify.models;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    public String generateToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 * 60);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, System.getenv("SECRET_KEY"))
                .compact();
    }

    public Claims getBody(String token) {
        return Jwts.parser().setSigningKey(System.getenv("SECRET_KEY"))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token) {
        return getBody(token).getSubject();
    }

    public Date getExpiration(String token) {
        return getBody(token).getExpiration();
    }

    public Boolean isValid(String token) {
        Date expirationDate = getExpiration(token);
        Instant expirationInst = expirationDate.toInstant();
        Instant nowInst = Instant.now();

        Duration duration = Duration.between(nowInst, expirationInst);

        return duration.abs().toHours() < 1;
    }
}
