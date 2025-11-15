package com.example.Beckend_EcoPulse.response;

import com.example.Beckend_EcoPulse.models.Event;
import com.example.Beckend_EcoPulse.models.enums.*;
import java.time.LocalDateTime;

public class EventDTO {
    private Long eventID;
    private String eventName;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private Double latitude;
    private Double longitude;
    private Long creatorID;
    private String creatorName;
    private EventStatus status;
    private EventVisibility visibility;
    private int participantCount;

    public EventDTO(Event event) {
        this.eventID = event.getEventID();
        this.eventName = event.getEventName();
        this.description = event.getDescription();
        this.startTime = event.getStartTime();
        this.endTime = event.getEndTime();
        this.location = event.getLocation();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.creatorID = event.getCreator().getUserID();
        this.creatorName = event.getCreator().getUsername();
        this.status = event.getStatus();
        this.visibility = event.getVisibility();
        this.participantCount = event.getParticipants().size();
    }

    // Getters and setters
}
