package org.example.mlooops.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.example.mlooops.dto.CustomUserDetails;
import org.example.mlooops.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

//0.12.3 버전 jwt
@Component
public class JWTUtil {
    private final CustomUserDetailsService customUserDetailsService;
    private SecretKey secretKey;
    private CustomUserDetailsService userDetailsService;
    public JWTUtil(@Value("${spring.jwt.secret}")String secret, CustomUserDetailsService userDetailsService, CustomUserDetailsService customUserDetailsService){
        this.secretKey=new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.userDetailsService=userDetailsService;
        this.customUserDetailsService = customUserDetailsService;
    }
    public String getEmail(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }
    public String getRoles(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token){
        try {
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        }
        catch (ExpiredJwtException e) {
            return true; // 토큰이 만료됨
        }
    }
    public Boolean RefreshIsExpired(String token){
        try {
            return !Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return false; // 토큰이 만료됨
        } catch (Exception e) {
            return false; // 토큰이 유효하지 않음
        }
    }

    public String generateNewToken(String refreshToken) {
        Claims claims=Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(refreshToken).getPayload();

        String email = claims.get("email", String.class);
        String role = claims.get("role", String.class);

        return Jwts.builder()
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*10L))
                .signWith(secretKey)
                .compact();
    }




    public String createJWT(String email, String role, Long expiredMs){
        return Jwts.builder()
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
    public String createRefreshJWT(String email, String role, Long expiredMS){
        return Jwts.builder()
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +expiredMS))
                .signWith(secretKey)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true; // 유효한 토큰
        } catch (ExpiredJwtException e) {
            return false; // 만료된 토큰
        } catch (Exception e) {
            return false; // 유효하지 않은 토큰
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        String email = claims.get("email", String.class);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }
}