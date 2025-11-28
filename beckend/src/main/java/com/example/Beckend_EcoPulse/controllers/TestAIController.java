package com.example.Beckend_EcoPulse.controllers;

import com.example.Beckend_EcoPulse.models.ChatHistory;
import com.example.Beckend_EcoPulse.services.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Acesta este "Recepționerul" principal al API-ului tău.
 * El preia cererile de la Android și le pasează la AIService.
 */
@RestController
@RequestMapping("/api/v1") // Toate endpoint-urile din această clasă vor începe cu /api/v1
public class TestAIController {

    // Serviciul care conține toată logica de business
    private final AIService aiService;

    // Spring va injecta automat AIService când pornește
    @Autowired
    public TestAIController(AIService aiService) {
        this.aiService = aiService;
    }

    /**
     * Endpoint-ul principal pentru a analiza o imagine.
     * Acesta este apelat de aplicația Android.
     * Așteaptă un fișier sub cheia "imageFile".
     */
    @PostMapping("/analyze-image")
    public ResponseEntity<Map<String, Object>> analyzeImage(
            @RequestParam("imageFile") MultipartFile imageFile) {

        try {
            // Trimite fișierul la serviciu pentru procesare
            Map<String, Object> response = aiService.obtineRaspunsImagine(imageFile);

            // Returnează răspunsul JSON (ex: {"result": "non_empty", ...})
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Dacă apare o eroare (ex: AI-ul e oprit), trimite un 500
            e.printStackTrace(); // Vezi eroarea în consola Spring
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Endpoint de test pentru a citi tot istoricul din baza de date.
     * Poate fi apelat din browser sau Postman.
     */
    @GetMapping("/history")
    public List<ChatHistory> getHistory() {
        // Cheamă serviciul pentru a cere datele din DB
        return aiService.obtineTotIstoricul();
    }

    /**
     * Endpoint de test pentru a scrie un rând în baza de date.
     * Poate fi apelat din browser pentru a verifica conexiunea DB.
     */
    @GetMapping("/test-db-save")
    public ChatHistory testDatabaseSave() {
        // Cheamă serviciul pentru a salva un rând de test
        return aiService.salveazaTestInDB("Test intrebare", "Test raspuns");
    }

    @PostMapping("/analyze-second-ai")
    public ResponseEntity<?> analyzeSecondAI(
            @RequestParam("imageFile") MultipartFile imageFile) {

        try {
            // Trimite fișierul la noua metodă din serviciu
            Map<String, Object> response = aiService.obtineRaspunsAI2(imageFile);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}

