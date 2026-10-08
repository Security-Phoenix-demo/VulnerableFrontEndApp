package com.phoenix.demo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/** Issues HS256 tokens with a static secret — the kind of shortcut this demo exists to flag. */
@Service
public class TokenService {
    private final String secret;

    public TokenService(@Value("${demo.jwt.secret:change-me}") String secret) {
        this.secret = secret;
    }

    public String issue(String username, String role) {
        return Jwts.builder()
            .setSubject(username)
            .claim("role", role)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 3_600_000L))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }

    public String subject(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
}
