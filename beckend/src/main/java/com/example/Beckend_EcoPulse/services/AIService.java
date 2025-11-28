package com.example.Beckend_EcoPulse.services;

import com.example.Beckend_EcoPulse.models.ChatHistory;
import com.example.Beckend_EcoPulse.repositories.ChatHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment; // <-- IMPORT NOU
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AIService {

    // --- Uneltele necesare ---
    private final ChatHistoryRepository chatRepository;
    private final RestTemplate restTemplate;

    // NOU: Injectăm întregul "mediu" de proprietăți
    private final Environment env;

    /**
     * CONSTRUCTORUL CORECTAT
     * Nu mai cere @Value, ci cere "Environment"
     */
    @Autowired
    public AIService(ChatHistoryRepository chatRepository,
                     RestTemplate restTemplate,
                     Environment env) { // <-- MODIFICAT

        this.chatRepository = chatRepository;
        this.restTemplate = restTemplate;
        this.env = env; // <-- MODIFICAT
    }

    // --- Logica pentru AI ---

    /**
     * Apelează PRIMUL server AI (cel de imagini)
     */
    public Map<String, Object> obtineRaspunsImagine(MultipartFile imageFile) throws IOException {

        // 1. Obține URL-ul din properties
        String aiImageUrl = env.getProperty("ai.service.image.url");
        if (aiImageUrl == null || aiImageUrl.isEmpty()) {
            throw new RuntimeException("URL-ul 'ai.service.image.url' lipsește din application.properties");
        }

        // 2. Setează headerele
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 3. Crează corpul
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // 4. Creează resursa "in-memory" (metoda sigură)
        ByteArrayResource imageResource = new ByteArrayResource(imageFile.getBytes()) {
            @Override
            public String getFilename() {
                return imageFile.getOriginalFilename();
            }
        };

        // --- AICI ESTE CHEIA PENTRU AI-UL #1 ---
        // Folosim "file" așa cum ai cerut
        body.add("file", imageResource);
        // ------------------------------------

        // 5. Creează entitatea
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 6. Apelează URL-ul
        Map<String, Object> response = restTemplate.postForObject(
                aiImageUrl, // Folosim variabila locală
                requestEntity,
                Map.class
        );

        // 7. Logica ta de salvare (este corectă și defensivă)
        if (response != null && response.containsKey("result")) {
            String rezultat = (String) response.get("result");
            ChatHistory istoric = new ChatHistory("Imagine Analizată", rezultat, LocalDateTime.now());
            chatRepository.save(istoric);
        } else {
            System.err.println("Răspuns neașteptat de la AI #1 (cel cu 'file'): " + response);
        }

        // 8. Returnează răspunsul
        return response;
    }

    /**
     * Apelează AL DOILEA server AI (cel de validare)
     */


    public Map<String, Object> obtineRaspunsAI2(MultipartFile imageFile) throws IOException {

        String aiSecondUrl = env.getProperty("ai.service.second.url");
        if (aiSecondUrl == null || aiSecondUrl.isEmpty()) {
            throw new RuntimeException("URL-ul 'ai.service.second.url' lipsește");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // --- AICI ESTE CORECȚIA CRITICĂ ---

        // 1. Creăm o resursă "in-memory" din fișierul primit,
        //    păstrând numele original al fișierului.
        ByteArrayResource imageResource = new ByteArrayResource(imageFile.getBytes()) {
            @Override
            public String getFilename() {
                return imageFile.getOriginalFilename();
            }
        };

        // 2. Adăugăm resursa direct. Cheia "image" este cea importantă.
        //    RestTemplate va crea automat header-ul Content-Disposition
        //    folosind "image" ca nume și numele fișierului de mai sus.
        body.add("image", imageResource);

        // ------------------------------------

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // APELEAZĂ NOUL URL
        Map<String, Object> response = restTemplate.postForObject(
                aiSecondUrl,
                requestEntity,
                Map.class
        );

        return response;
    }


    // --- Logica pentru Baza de Date (Testare) ---

    public List<ChatHistory> obtineTotIstoricul() {
        return chatRepository.findAll();
    }

    public ChatHistory salveazaTestInDB(String intrebare, String raspuns) {
        ChatHistory istoric = new ChatHistory("Test intrebare", "Test raspuns", LocalDateTime.now());
        return chatRepository.save(istoric);
    }
}