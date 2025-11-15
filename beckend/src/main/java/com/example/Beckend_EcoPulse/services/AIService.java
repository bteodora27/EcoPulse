package com.example.Beckend_EcoPulse.services;

import com.example.Beckend_EcoPulse.models.ChatHistory;
import com.example.Beckend_EcoPulse.repositories.ChatHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment; // <-- IMPORT NOU
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

        // NOU: Obține URL-ul doar când ai nevoie de el
        String aiImageUrl = env.getProperty("ai.service.image.url");
        if (aiImageUrl == null || aiImageUrl.isEmpty()) {
            throw new RuntimeException("URL-ul 'ai.service.image.url' lipsește din application.properties");
        }

        // ... (restul codului tău de creare a cererii este corect) ...
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new HttpEntity<>(imageFile.getResource(),
                createFilenameHeader(imageFile.getOriginalFilename())));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Apelăm URL-ul
        Map<String, Object> response = restTemplate.postForObject(
                aiImageUrl, // <-- Folosim variabila locală
                requestEntity,
                Map.class
        );

        // Logica ta de salvare (este corectă)
        String rezultat = (String) response.get("result");
        ChatHistory istoric = new ChatHistory("Imagine Analizată", rezultat, LocalDateTime.now());
        chatRepository.save(istoric);

        return response;
    }

    /**
     * Apelează AL DOILEA server AI (cel de validare)
     */
    public Map<String, Object> obtineRaspunsAI2(MultipartFile imageFile) throws IOException {

        // NOU: Obține URL-ul doar când ai nevoie de el
        String aiSecondUrl = env.getProperty("ai.service.second.url");
        if (aiSecondUrl == null || aiSecondUrl.isEmpty()) {
            throw new RuntimeException("URL-ul 'ai.service.second.url' lipsește din application.properties");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new HttpEntity<>(imageFile.getResource(),
                createFilenameHeader(imageFile.getOriginalFilename())));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // APELEAZĂ NOUL URL
        Map<String, Object> response = restTemplate.postForObject(
                aiSecondUrl, // <-- Folosim variabila locală
                requestEntity,
                Map.class
        );

        return response;
    }

    // Funcție ajutătoare (rămâne neschimbată)
    private HttpHeaders createFilenameHeader(String filename) {
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "form-data; name=file; filename=\"" + filename + "\"");
        return header;
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