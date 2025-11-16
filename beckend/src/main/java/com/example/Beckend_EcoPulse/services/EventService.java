package com.example.Beckend_EcoPulse.services;

import com.example.Beckend_EcoPulse.models.*;
import com.example.Beckend_EcoPulse.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.example.Beckend_EcoPulse.requests.EventRequest; // <-- IMPORT NOU
import java.time.LocalDateTime;
import java.io.IOException;
import java.util.Map;

@Service
public class EventService {

    @Autowired private EventRepository eventRepository;
    @Autowired private EventParticipantRepository participantRepository;
    @Autowired private StandardUserRepository standardUserRepository;
    @Autowired private AIService aiService;
    @Autowired private UserRepository userRepository;


    // Injectează serviciul existent de curățenie pentru helper-ul de rang
    @Autowired private CleaningService cleaningService;

    /**
     * Endpoint: /events/{eventId}/join
     */
    @Transactional
    public EventParticipant joinEvent(Long userId, Long eventId) {

        // 1. Găsește entitățile reale
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu a fost găsit. ID: " + userId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evenimentul nu a fost găsit. ID: " + eventId));

        // 2. Crează cheia compusă
        EventParticipantId id = new EventParticipantId(); // Clasa ta de cheie compusă
        id.setUserId(userId);
        id.setEventId(eventId);

        // 3. Verifică dacă e deja înscris
        if (participantRepository.existsById(id)) {
            throw new RuntimeException("Utilizatorul este deja înscris la acest eveniment.");
        }

        // 4. Creează și setează legăturile
        EventParticipant participant = new EventParticipant();
        participant.setId(id);
        participant.setUser(user);   // <-- ATAȘEAZĂ PĂRINTELE USER
        participant.setEvent(event); // <-- ATAȘEAZĂ PĂRINTELE EVENT
        participant.setUserRole("participant");

        return participantRepository.save(participant);
    }

    /**
     * Logica de colectare saci și alocare puncte.
     */
    @Transactional
    public Map<String, Object> submitBag(Long userId, Long eventId, MultipartFile bagPhoto) throws IOException {

        // 1. Verifică dacă userul este participant valid
        EventParticipantId participantId = new EventParticipantId();
        participantId.setUserId(userId);
        participantId.setEventId(eventId);

        EventParticipant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Utilizatorul nu este înscris la acest eveniment."));

        // 2. Numără sacii cu AI-ul #1 (Contorul)
        Map<String, Object> bagsResponse = aiService.obtineRaspunsImagine(bagPhoto);
        Object countObj = bagsResponse.getOrDefault("total_bags", 0);
        Integer bagCount = (countObj instanceof Number) ? ((Number) countObj).intValue() : 0;

        if (bagCount <= 0) {
            return Map.of("message", "Sac invalid sau nu a fost detectat gunoi.");
        }

        // 3. Calculează punctele și actualizează profilul utilizatorului
        int pointsGained = bagCount * 10;

        StandardUser profile = standardUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profilul utilizatorului nu a fost găsit."));

        profile.setTotalPoints(profile.getTotalPoints() + pointsGained);
        cleaningService.updateUserRank(profile); // Refolosim helper-ul de rang de la CleaningService
        standardUserRepository.save(profile);

        // 4. Actualizează totalul evenimentului (CRUCIAL pentru 5% organizator)
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Evenimentul nu a fost găsit."));

        event.setTotalPoints(event.getTotalPoints() + pointsGained);
        eventRepository.save(event);

        // 5. Returnează un răspuns de succes
        return Map.of(
                "message", "Sac adăugat cu succes!",
                "pointsGained", pointsGained,
                "newTotalPoints", profile.getTotalPoints()
        );
    }
    @Transactional
    public Event createEvent(EventRequest request) {

        // 1. Găsește utilizatorul Creator (Organizatorul)
        User creator = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Utilizatorul (Creatorul) nu a fost găsit. ID: " + request.getCreatorId()));

        // (Opțional) Verifică dacă acest user este o Organizație
        if (!"organization".equalsIgnoreCase(creator.getUserType())) {
            throw new RuntimeException("Doar organizațiile pot crea evenimente.");
        }

        // 2. Creează entitatea Event
        Event newEvent = new Event();
        newEvent.setCreator(creator); // Setează legătura

        newEvent.setEventName(request.getEventName());
        newEvent.setDescription(request.getDescription());
        newEvent.setLocation(request.getLocation());
        newEvent.setStartTime(request.getStartTime());
        newEvent.setEndTime(request.getEndTime());
        newEvent.setVisibility(request.getVisibility());
        newEvent.setStatus(request.getStatus());

        // Setează valorile implicite conform diagramei tale
        newEvent.setTotalPoints(0);
        // 'creationTime' se setează automat de @CreationTimestamp

        // 3. Salvează în baza de date
        return eventRepository.save(newEvent);
    }
}