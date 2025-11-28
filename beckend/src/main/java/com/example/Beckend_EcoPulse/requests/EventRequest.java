package com.example.Beckend_EcoPulse.requests;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventRequest {

    // Câmpurile din diagrama ta UML 'events'
    private String eventName;
    private String description;
    private String location; // Sau poți cere lat/long

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String visibility; // "public" sau "volunteers"
    private String status; // "upcoming", "ongoing", etc.

    // ID-ul utilizatorului (ORGANIZATORUL) care creează evenimentul
    private Long creatorId;
}