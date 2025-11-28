package com.example.Beckend_EcoPulse.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "EventParticipants")
public class EventParticipant {

    @EmbeddedId // Utilizează clasa cheie compusă de mai sus
    private EventParticipantId id;

    // Relația cu tabelul User
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId") // Mapează câmpul userId din cheia compusă
    @JoinColumn(name = "userID", insertable = false, updatable = false)
    private User user;

    // Relația cu tabelul Event
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId") // Mapează câmpul eventId din cheia compusă
    @JoinColumn(name = "eventID", insertable = false, updatable = false)
    private Event event;

    // Câmpuri din diagrama UML:
    private String userRole;
    private String feedback;
    private Integer rating;

    public EventParticipant() {}
}