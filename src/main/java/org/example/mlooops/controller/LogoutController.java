package org.example.mlooops.controller;

import org.example.mlooops.service.JwtBlacklistService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    private JwtBlacklistService jwtBlacklistService;

    public LogoutController(JwtBlacklistService jwtBlacklistService) {
        this.jwtBlacklistService = jwtBlacklistService;
    }

    @PostMapping("/api/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.split(" ")[1];
            jwtBlacklistService.addToBlacklist(token);
        }

        // 세션에서 인증 정보 제거
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        return ResponseEntity.ok("로그아웃 성공");
    }
}