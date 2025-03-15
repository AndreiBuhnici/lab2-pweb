package com.example.lab2.service;

import com.example.lab2.model.UserEntity;
import com.example.lab2.model.UserRoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final String secret_key = "d4aa5e28fab39c8a049e0e2a3b0f69c834d1fae6f9d399fb920177b53133ef6d";
    private final long expiration_time = 864_000_000; // 10 days

    public String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        return buildToken(claims);
    }

    private String buildToken(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration_time))
                .signWith(getSignKey())
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
       Claims claims = extractAllClaims(token);
       return (String) claims.get("email");
    }

    public UserRoleEnum extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return UserRoleEnum.valueOf((String) claims.get("role"));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserEntity user) {
        final String username = extractUsername(token);
        final UserRoleEnum role = extractRole(token);
        return (username.equals(user.getUsername()) && role.name().equals(user.getRole().toString()) && !isTokenExpired(token));
    }
}