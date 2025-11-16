package com.example.Beckend_EcoPulse.controllers;

import com.example.Beckend_EcoPulse.models.User;
import com.example.Beckend_EcoPulse.requests.LoginRequest;
import com.example.Beckend_EcoPulse.requests.SignUpRequest;
import com.example.Beckend_EcoPulse.services.AuthService; // NOU: Importă serviciul
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Beckend_EcoPulse.requests.OrganizationSignUpRequest;

import java.util.Map;
// Importurile pentru UserRepository și PasswordEncoder nu mai sunt necesare aici

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService; // NOU: Injectează Serviciul

    public AuthController(AuthService authService) { // NOU: Constructorul primește serviciul
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest request) {
        try {
            // Apelează logica complexă din serviciu
            User savedUser = authService.registerStandardUser(request);
            return ResponseEntity.status(201).body(Map.of(
                    "message", "Cont creat cu succes.",
                    "userId", savedUser.getId().toString()
            ));

        } catch (RuntimeException e) {
            // Prinde erorile (ex: "Email în uz") aruncate de serviciu
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Primește Map-ul cu token-ul și ID-ul
            Map<String, Object> loginData = authService.loginUser(loginRequest);

            // 2. Returnează JSON-ul final pentru Android
            return ResponseEntity.ok(Map.of(
                    "message", "Autentificare reușită!",
                    "accessToken", (String) loginData.get("accessToken"),
                    // Converteste ID-ul la String (pentru siguranța JSON)
                    "userId", loginData.get("userId").toString()
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    @PostMapping("/signup-organization")
    public ResponseEntity<?> registerOrganization(@RequestBody OrganizationSignUpRequest request) {
        try {
            // Apelează noua logică din serviciu
            User savedUser = authService.registerOrganization(request);
            return ResponseEntity.status(201).body(Map.of(
                    "message", "Cont organizație creat cu succes.",
                    "userId", savedUser.getId().toString()
            ));

        } catch (RuntimeException e) {
            // Prinde erorile (ex: "Email în uz")
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}