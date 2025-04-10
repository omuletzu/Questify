package com.example.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;

    public String generateToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 * 60);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims getBody(String token) {
        return Jwts.parser().setSigningKey(secretKey)
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
        if(token == null) {
            return false;
        }

        try {
            Date expirationDate = getExpiration(token);
            Instant expirationInst = expirationDate.toInstant();
            Instant nowInst = Instant.now();

            Duration duration = Duration.between(nowInst, expirationInst);

            return duration.abs().toHours() < 1;
        }
        catch (Exception e) {
            return false;
        }
    }
}
