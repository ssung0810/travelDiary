package com.ssung.travelDiary.handler;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtHandler {

    private String type = "Bearer";

    public String createToken(String encodedKey, String subject, long maxAgeSeconds) {
        Date date = new Date();
        return type + Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + maxAgeSeconds * 1000))
                .signWith(SignatureAlgorithm.HS256, encodedKey)
                .compact();
    }

    public String expectedSubject(String encodedKey, String token) {
        return parse(encodedKey, token).getBody().getSubject();
    }

    public boolean validate(String encodedKey, String token) {
        try {
            parse(encodedKey, token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Jws<Claims> parse(String key, String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(unType(token));
    }

    private String unType(String token) {
        return token.substring(type.length());
    }
}
