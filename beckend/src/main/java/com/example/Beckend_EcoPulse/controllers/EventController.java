package com.example.Beckend_EcoPulse.controllers;

import com.example.Beckend_EcoPulse.models.Event;
import com.example.Beckend_EcoPulse.requests.EventRequest;
import com.example.Beckend_EcoPulse.requests.EventResponseDTO;
import com.example.Beckend_EcoPulse.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    @Autowired
    private EventService eventService;

    // Endpoint pentru a vedea lista de evenimente (simulare)
    @GetMapping("/")
    public ResponseEntity<List<EventResponseDTO>> getAllEvents() {
        // Acum returnează lista de DTO-uri din serviciu
        List<EventResponseDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    // Endpoint pentru ca un utilizator să se înscrie la eveniment
    // POST /api/v1/events/{eventId}/join?userId={userId}
    @PostMapping("/{eventId}/join")
    public ResponseEntity<?> joinEvent(@PathVariable Long eventId, @RequestParam Long userId) {
        try {
            eventService.joinEvent(userId, eventId);
            return ResponseEntity.ok("Înscriere reușită la evenimentul cu ID: " + eventId);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Eroare internă la înscriere."));
        }
    }

    // Endpoint pentru a trimite un sac și a aduna puncte
    // POST /api/v1/events/{eventId}/submit-bag?userId={userId} (cu form-data)
    @PostMapping("/{eventId}/submit-bag")
    public ResponseEntity<?> submitBag(
            @PathVariable Long eventId,
            @RequestParam Long userId,
            @RequestParam("bagPhoto") MultipartFile bagPhoto) {
        try {
            Map<String, Object> response = eventService.submitBag(userId, eventId, bagPhoto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Eroare: Userul nu e înscris, Sac invalid
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Eroare internă la trimiterea sacului."));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(@RequestBody EventRequest request) {
        try {
            // Pasează la serviciu
            Event newEvent = eventService.createEvent(request);

            return ResponseEntity.status(201).body(Map.of(
                    "message", "Eveniment creat cu succes.",
                    "eventId", newEvent.getId()
            ));
        } catch (Exception e) {
            // Prinde erorile (ex: "Creatorul nu a fost găsit", "Doar organizațiile...")
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}