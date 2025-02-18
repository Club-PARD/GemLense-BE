package com.example.longkathon.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    // ✅ JWT에서 Claims(페이로드) 추출
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ✅ 사용자 이름(username) 추출
    public String getUsername(String token) {
        return parseClaims(token).get("username", String.class);
    }

    // ✅ 사용자 역할(role) 추출
    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // ✅ 토큰 만료 여부 확인
    public Boolean isExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    // ✅ 새로운 JWT 생성
    public String createJwt(String username, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
