package org.example.mlooops.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DatabindException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.mlooops.dto.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil){
        this.authenticationManager=authenticationManager;
        this.jwtUtil=jwtUtil;
    }

    private final ObjectMapper objectMapper=new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        try{
            Map<String, String> requestBody=objectMapper.readValue(request.getInputStream(), Map.class);
            String email=requestBody.get("email");
            String password=requestBody.get("password");
            UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(email, password, null);

            return authenticationManager.authenticate(authToken);

        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //    로그인 진행시 로그인 성공, 실패에 대한 처리를 아래의 함수에서 작성.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        //추후에 여기에서 jwt 토큰 발급 관련처리를 진행.
        CustomUserDetails customUserDetails=(CustomUserDetails) authentication.getPrincipal();
        String email=customUserDetails.getEmail();
        String username=customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJWT(email, role, 1000*60*60*10L); //1 hour
        String refreshToken = jwtUtil.createRefreshJWT(email, role, 1000*60*60*24*7L); // 24 hours
        System.out.println(token);
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("refreshToken","Bearer " +  refreshToken);

        // 사용자 정보를 JSON 형식으로 응답 본문에 추가
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 사용자 정보 객체 생성
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", email);
        userInfo.put("role", role);
        userInfo.put("name", username); // 예시: 사용자 이름

        // JSON 응답 작성
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(userInfo);

        // 응답 작성
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();

    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);

    }

}