package com.example.Beckend_EcoPulse.requests;

import com.example.Beckend_EcoPulse.models.Event;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventResponseDTO {

    // Câmpurile pe care vrei să le vadă frontend-ul
    private Long eventId;
    private String eventName;
    private String description;
    private String location;
    private LocalDateTime startTime;
    private String status;
    private String visibility;
    private Long creatorId; // Trimitem doar ID-ul, nu tot user-ul
    private String creatorUsername; // Bonus: trimitem și numele

    // Constructor care mapează Entitatea la DTO
    public EventResponseDTO(Event event) {
        this.eventId = event.getId();
        this.eventName = event.getEventName();
        this.description = event.getDescription();
        this.location = event.getLocation();
        this.startTime = event.getStartTime();
        this.status = event.getStatus();
        this.visibility = event.getVisibility();
        this.creatorId = event.getCreator().getId();
        this.creatorUsername = event.getCreator().getUsername();
    }
}