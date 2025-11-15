package com.example.Beckend_EcoPulse.controllers;

import com.example.Beckend_EcoPulse.services.EventParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventParticipationController {

    @Autowired
    private EventParticipationService participationService;

    @PostMapping("/{eventID}/join")
    public ResponseEntity<String> joinEvent(@PathVariable Long eventID, @RequestHeader("User-ID") Long userID) {
        participationService.joinEvent(eventID, userID);
        return ResponseEntity.ok("User joined the event successfully");
    }
}
