package org.example.mlooops.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mlooops.jwt.JWTFilter;
import org.example.mlooops.jwt.JWTUtil;
import org.example.mlooops.jwt.LoginFilter;
import org.example.mlooops.service.JwtBlacklistService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private JWTUtil jwtUtil;
    private ObjectMapper objectMapper=new ObjectMapper();
    private JwtBlacklistService jwtBlacklistService;
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, JwtBlacklistService jwtBlacklistService){
        this.authenticationConfiguration=authenticationConfiguration;
        this.jwtBlacklistService=jwtBlacklistService;
        this.jwtUtil=jwtUtil;
    }



    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//csrf disable
        http
                .csrf((auth)-> auth.disable());
//From 로그인 방식 disable
        http
                .formLogin((auth)-> auth.disable());
//http basic 인증 방식 disable
        http
                .httpBasic((auth)->auth.disable());
//경로별 인가 작업
        http
                .authorizeHttpRequests((auth)-> auth
//                        .requestMatchers("/login", "/test", "/join", "/interest/join").permitAll()
//                        .anyRequest().authenticated());
                        .requestMatchers("/login", "/test", "/join", "/interest/join", "/interest/init").permitAll()
                        .anyRequest().authenticated());
        http
                .addFilterBefore(new JWTFilter(jwtUtil, objectMapper,jwtBlacklistService), LoginFilter.class);


        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);


        //세션 설정
        http
                .sessionManagement((session)->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}