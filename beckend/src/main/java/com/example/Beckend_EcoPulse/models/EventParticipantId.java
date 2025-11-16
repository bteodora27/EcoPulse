package com.example.Beckend_EcoPulse.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable // Marchează ca fiind o cheie compusă
public class EventParticipantId implements Serializable {

    @Column(name = "userID")
    private Long userId;

    @Column(name = "eventID")
    private Long eventId;

    public EventParticipantId() {}
}