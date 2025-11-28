package com.example.Beckend_EcoPulse.config; // Asigură-te că pachetul e corect

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Bean-ul tău pentru criptarea parolelor (este corect)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean-ul pentru regulile de securitate (AICI E CORECȚIA)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // --- ACEASTA ESTE LINIA CRITICĂ PENTRU EROAREA 403 ---
                // Spune-i Spring Security SĂ NU MAI BLocheze cererile POST
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/ping", "/api/v1/history", "/api/v1/test-db-save").permitAll()
                        .requestMatchers("/api/v1/analyze-image").permitAll()
                        .requestMatchers("/api/v1/analyze-second-ai").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/cleaning/**").permitAll()
                        .requestMatchers("/api/v1/events/**").permitAll()
                        .anyRequest().authenticated()
                )

                // Spune-i să nu folosească Sesiuni (pentru JWT/Token)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}