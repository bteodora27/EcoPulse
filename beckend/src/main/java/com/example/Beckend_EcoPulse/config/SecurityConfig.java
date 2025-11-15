package com.example.Beckend_EcoPulse.config; // Asigură-te că pachetul este corect!

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // <-- ACEASTA ESTE ESENȚIALĂ pentru a crea @Bean-uri
public class SecurityConfig {

    @Bean // <-- Acum @Bean este în interiorul unei clase @Configuration
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. CRITIC: Dezactivează protecția CSRF (necesar pentru POST/PUT din Postman sau Android)
                .csrf(AbstractHttpConfigurer::disable)

                // Dezactivează formularele de login implicite
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // 2. STABILEȘTE REGULILE DE ACCES (Ordinea este crucială)
                .authorizeHttpRequests(auth -> auth

                        // Permite accesul public la TOATE căile care încep cu /auth/
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // Permite accesul public la endpoint-urile de test
                        .requestMatchers("/api/v1/ping", "/api/v1/history", "/api/v1/test-db-save").permitAll()

                        // Toate celelalte cereri necesită autentificare (login)
                        .anyRequest().authenticated()
                )

                // Spune-i Spring să nu folosească sesiuni (pentru că vei folosi Token-uri)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}