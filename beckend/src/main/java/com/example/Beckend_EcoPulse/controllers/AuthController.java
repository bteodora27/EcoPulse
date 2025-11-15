package com.example.Beckend_EcoPulse.controllers;

import com.example.Beckend_EcoPulse.models.User;
import com.example.Beckend_EcoPulse.requests.LoginRequest;
import com.example.Beckend_EcoPulse.requests.SignUpRequest;
import com.example.Beckend_EcoPulse.services.AuthService; // NOU: Importă serviciul
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            // Apelează noua logică de login din serviciu
            String accessToken = authService.loginUser(loginRequest);

            // Returnează token-ul către Android
            return ResponseEntity.ok(Map.of(
                    "message", "Autentificare reușită!",
                    "accessToken", accessToken
            ));

        } catch (RuntimeException e) {
            // Prinde erorile (ex: "Credentiale invalide")
            return ResponseEntity.status(401).body(e.getMessage());
        }

        // ... (Metoda ta de /login rămâne (în mare parte) la fel) ...
        // (Deși ar fi bine să muți și logica de login tot în AuthService)
    }
}