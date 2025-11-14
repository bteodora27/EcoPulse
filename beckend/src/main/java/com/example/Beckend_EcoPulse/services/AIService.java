package com.example.Beckend_EcoPulse.services;

import com.example.Beckend_EcoPulse.models.ChatHistory;
import com.example.Beckend_EcoPulse.repositories.ChatHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

/**
 * Acesta este "Creierul" aplicației tale (Managerul).
 * Conține toată logica de business: cum să apelezi AI-ul
 * și cum să vorbești cu baza de date.
 */
@Service
public class AIService {

    // --- Uneltele necesare ---

    // 1. Unealta de a vorbi cu Baza de Date
    private final ChatHistoryRepository chatRepository;

    // 2. Unealta de a apela alte API-uri (serverul Python)
    private final RestTemplate restTemplate;

    // --- Adresele URL (injectate din application.properties) ---

    // Adresa serverului Python de imagini
    private final String AI_IMAGE_URL;

    /**
     * Constructorul care primește "uneltele" de la Spring
     * (Dependency Injection)
     */
    @Autowired
    public AIService(ChatHistoryRepository chatRepository,
                     RestTemplate restTemplate,
                     @Value("${ai.service.image.url}") String aiImageUrl) {

        this.chatRepository = chatRepository;
        this.restTemplate = restTemplate;
        this.AI_IMAGE_URL = aiImageUrl;
    }

    // --- Logica pentru AI ---

    /**
     * Apelează serverul AI Python trimițând un fișier imagine.
     * Această metodă este apelată de AIController.
     */
    public Map<String, Object> obtineRaspunsImagine(MultipartFile imageFile) throws IOException {

        // 1. Setăm headerele. Trebuie să fie 'multipart/form-data'.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 2. Creăm corpul cererii (Body-ul)
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // 3. Adăugăm fișierul.
        // Numele "file" TREBUIE să se potrivească cu cel din Python: (file: UploadFile)
        body.add("file", new HttpEntity<>(imageFile.getResource(),
                createFilenameHeader(imageFile.getOriginalFilename())));

        // 4. Combinăm corpul și headerele
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 5. Apelăm serverul Python
        // Ne așteptăm la un Map (JSON) ca răspuns
        Map<String, Object> response = restTemplate.postForObject(
                AI_IMAGE_URL,
                requestEntity,
                Map.class
        );

        String rezultat = (String) response.get("result"); // sau cum se numește cheia

        // 7. Creează un obiect de istoric
        // (Folosești "Imagine Analizată" ca "întrebare" de test)
        ChatHistory istoric = new ChatHistory("Imagine Analizată", rezultat, LocalDateTime.now());

        // 8. Salvează-l în Baza de Date!
        chatRepository.save(istoric);
        // --- SFÂRȘIT MODIFICARE ---

        // 9. Returnează răspunsul original al AI-ului către Android
        return response;
    }

    // Funcție ajutătoare pentru a seta numele fișierului în headerul multipart
    private HttpHeaders createFilenameHeader(String filename) {
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "form-data; name=file; filename=\"" + filename + "\"");
        return header;
    }


    // --- Logica pentru Baza de Date (Testare) ---

    /**
     * TEST DB (READ): Citește DOAR din Baza de Date.
     * Apelată de AIController.
     */
    public List<ChatHistory> obtineTotIstoricul() {
        return chatRepository.findAll();
    }

    /**
     * TEST DB (WRITE): Scrie DOAR în Baza de Date.
     * Apelată de AIController.
     */
    public ChatHistory salveazaTestInDB(String intrebare, String raspuns) {
        ChatHistory istoric = new ChatHistory(intrebare, raspuns, LocalDateTime.now());
        // Metoda .save() este oferită "magic" de JpaRepository
        return chatRepository.save(istoric);
    }
}