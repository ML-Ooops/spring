package org.example.mlooops.jwt;

import org.example.mlooops.dto.CustomUserDetails;
import org.example.mlooops.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.mlooops.service.JwtBlacklistService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
//    private final ObjectMapper objectMapper;

    private JwtBlacklistService jwtBlacklistService;
    public JWTFilter(JWTUtil jwtUtil, ObjectMapper objectMapper, JwtBlacklistService jwtBlacklistService) {
        this.jwtBlacklistService=jwtBlacklistService;
        this.jwtUtil = jwtUtil;
//        this.objectMapper=objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("토큰 검사 시작");
        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        // 로그아웃 검증
        if (jwtBlacklistService.isBlacklisted(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("로그아웃된 사용자입니다.");
            return;
        }
        
        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token) && token != null) {


            System.out.println("token expired");
            String refreshToken = request.getHeader("refreshToken").split(" ")[1];;


            if (refreshToken != null && jwtUtil.RefreshIsExpired(refreshToken)) {
                //리프레시 토큰ok, 일반 토큰 X
                System.out.println("토큰 재발급");
                String newToken = jwtUtil.generateNewToken(refreshToken); // 새로운 Access Token 발급
                response.setStatus(303);
                response.addHeader("Authorization", "Bearer " + newToken);




            } else {
                // Refresh Token이 없거나 유효하지 않으면 401 응답
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Refresh token has expired");
            }
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }
        //토큰에서 username과 role 획득
        String email = jwtUtil.getEmail(token);
        String role = jwtUtil.getRoles(token);

        //userEntity를 생성하여 값 set
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPasswordHash("temppassword");
        userEntity.setRole(role);

        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}