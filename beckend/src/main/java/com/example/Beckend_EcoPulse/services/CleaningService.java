package com.example.Beckend_EcoPulse.services;

import com.example.Beckend_EcoPulse.models.CleaningSession;
import com.example.Beckend_EcoPulse.models.StandardUser;
import com.example.Beckend_EcoPulse.models.User;
import com.example.Beckend_EcoPulse.repositories.CleaningSessionRepository;
import com.example.Beckend_EcoPulse.repositories.StandardUserRepository;
import com.example.Beckend_EcoPulse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.ResourceAccessException; // <-- Adăugat pentru erori de rețea

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CleaningService {

    // --- Injectarea tuturor uneltelor necesare ---
    @Autowired private CleaningSessionRepository sessionRepository;
    @Autowired private StandardUserRepository standardUserRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private AIService aiService;

    // --- Utils ---
    private String saveFile(MultipartFile file) {
        if (file == null) return null;
        return "simulated_path/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
    }

    // --- METODA 1: ÎNCEPEREA SESIUNII ---
    @Transactional
    public CleaningSession startSession(Long userId, MultipartFile beforePhoto, Double latitude, Double longitude) throws IOException {

        // 1. Găsește utilizatorul
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // 2. Validează poza "înainte" cu AI-ul #2 (Validatorul)
        try {
            Map<String, Object> validationResponse = aiService.obtineRaspunsAI2(beforePhoto);
            String validationResult = (String) validationResponse.get("result");

            // Logică: Dacă AI-ul returnează "clean", nu începem sesiunea.
            if ("clean".equalsIgnoreCase(validationResult)) {
                throw new RuntimeException("Zona nu pare să necesite curățare. Poza 'înainte' nu este validă.");
            }
        } catch (ResourceAccessException e) {
            // Aruncă o eroare specifică dacă AI-ul nu e pornit/accesibil
            throw new RuntimeException("Eroare de conexiune cu AI Validator (AI #2). Asigură-te că rulează.");
        }


        // 3. Salvează poza (simulat)
        String beforePhotoUrl = saveFile(beforePhoto);

        // 4. Creează și salvează sesiunea
        CleaningSession session = new CleaningSession();
        session.setUser(user);
        session.setBeforePhotoUrl(beforePhotoUrl);
        session.setLatitude(latitude);
        session.setLongitude(longitude);
        session.setStatus("STARTED"); // Setează starea ca "ÎNCEPUT"

        return sessionRepository.save(session);
    }

    // --- METODA 2: ÎNCHEIEREA SESIUNII ---
    @Transactional
    public Map<String, Object> endSession(Long sessionId, MultipartFile bagsPhoto, MultipartFile afterPhoto) throws IOException {

        // 1. Găsește sesiunea activă
        CleaningSession session = sessionRepository.findByIdAndStatus(sessionId, "STARTED")
                .orElseThrow(() -> new RuntimeException("Sesiunea nu este activă sau nu a fost găsită (ID: " + sessionId + ")"));

        // 2. Validează poza "după" cu AI-ul #2 (Validatorul)
        Map<String, Object> afterPhotoResponse = aiService.obtineRaspunsAI2(afterPhoto);
        String afterResult = (String) afterPhotoResponse.get("result");

        if ("dirty".equalsIgnoreCase(afterResult)) {
            throw new RuntimeException("Zona nu pare curățată. Poza 'după' nu este validă.");
        }

        // 3. Numără sacii cu AI-ul #1 (Contorul)
        Map<String, Object> bagsResponse = aiService.obtineRaspunsImagine(bagsPhoto);

        Object countObj = bagsResponse.getOrDefault("total_bags", 0); // Corectat la "non_empty_bags"
        Integer bagCount = 0;
        if (countObj instanceof Number) {
            bagCount = ((Number) countObj).intValue();
        }

        if (bagCount <= 0) {
            throw new RuntimeException("Nu a fost detectat niciun sac valid de gunoi. Sesiune încheiată fără puncte.");
        }

        // 4. Calculează punctele și actualizează profilul utilizatorului
        int pointsGained = bagCount * 10;

        StandardUser profile = standardUserRepository.findById(session.getUser().getId())
                .orElseThrow(() -> new RuntimeException("Profilul utilizatorului nu a fost găsit."));

        profile.setTotalPoints(profile.getTotalPoints() + pointsGained);
        updateUserRank(profile); // Actualizează rangul
        standardUserRepository.save(profile);

        // 5. Salvează pozele și închide sesiunea
        session.setBagsPhotoUrl(saveFile(bagsPhoto));
        session.setAfterPhotoUrl(saveFile(afterPhoto));
        session.setBagCount(bagCount);
        session.setPointsGained(pointsGained);
        session.setStatus("COMPLETED");
        session.setEndTime(LocalDateTime.now());
        sessionRepository.save(session);

        // 6. Returnează un răspuns de succes către Android
        return Map.of(
                "message", "Sesiune încheiată cu succes!",
                "pointsGained", pointsGained,
                "bagCount", bagCount,
                "newTotalPoints", profile.getTotalPoints(),
                "newRank", profile.getStandardUserRank()
        );
    }

    // --- METODA 3: ACTUALIZARE RANG (Helper) ---
    private void updateUserRank(StandardUser profile) {
        int points = profile.getTotalPoints();

        if (points >= 500) {
            profile.setStandardUserRank("Tree");
        } else if (points >= 200) {
            profile.setStandardUserRank("Sapling");
        } else if (points >= 50) {
            profile.setStandardUserRank("Sprout");
        } else if (points >= 0) {
            profile.setStandardUserRank("Seed");
        }
    }

    // --- UTILS ---

}