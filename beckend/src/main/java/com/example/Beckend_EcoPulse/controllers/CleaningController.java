package com.example.Beckend_EcoPulse.controllers;

import com.example.Beckend_EcoPulse.models.CleaningSession;
import com.example.Beckend_EcoPulse.requests.SessionStartResponseDTO;
import com.example.Beckend_EcoPulse.services.CleaningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/cleaning")
public class CleaningController {

    @Autowired
    private CleaningService cleaningService;

    /**
     * Endpoint-ul pentru a ÎNCEPE o sesiune de curățare.
     * Așteaptă 3 părți: userId, zoneId și poza "înainte".
     */
    @PostMapping("/start")
    public ResponseEntity<?> startCleaningSession(
            @RequestParam("user_id") Long userId,
            @RequestParam("beforePhoto") MultipartFile beforePhoto,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude) {
        try {
            // 1. Apelează Serviciul (Serviciul returnează Entitatea JPA)
            CleaningSession session = cleaningService.startSession(
                    userId, beforePhoto, latitude, longitude
            );

            // 2. Mapează Entitatea JPA la DTO-ul de Răspuns (Filtrarea)
            SessionStartResponseDTO dto = new SessionStartResponseDTO();
            dto.setSessionId(session.getId());
            dto.setUserId(session.getUser().getId());
            // 3. Trimite DTO-ul filtrat (doar datele sigure și necesare)
            return ResponseEntity.status(201).body(dto);

        } catch (Exception e) {
            // 4. Returnează eroarea (dacă AI-ul sau logica eșuează)
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Endpoint-ul pentru a ÎNCHEIA o sesiune.
     * Așteaptă 3 părți: sessionId (din URL), poza cu sacii și poza "după".
     */
    @PostMapping("/{sessionId}/end")
    public ResponseEntity<?> endCleaningSession(
            @PathVariable Long sessionId,
            @RequestParam("bagsPhoto") MultipartFile bagsPhoto,
            @RequestParam("afterPhoto") MultipartFile afterPhoto) {
        try {
            // Pasează la serviciu
            Object response = cleaningService.endSession(sessionId, bagsPhoto, afterPhoto);
            // Returnează 200 OK cu sumarul (puncte, saci, etc.)
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Returnează 400 Bad Request dacă ceva eșuează
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}