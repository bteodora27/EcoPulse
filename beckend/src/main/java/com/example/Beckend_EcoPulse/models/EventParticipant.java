package com.example.Beckend_EcoPulse.models;

import com.example.Beckend_EcoPulse.models.enums.ParticipantRole;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "EventParticipants")
public class EventParticipant {

    public EventParticipant(EventParticipantId id, User user, Event event,
                            ParticipantRole userRole, String feedback, Integer rating, LocalDateTime joinedAt) {
        this.id = id;
        this.user = user;
        this.event = event;
        this.userRole = userRole;
        this.feedback = feedback;
        this.rating = rating;
        this.joinedAt = joinedAt;
    }

    @EmbeddedId
    private EventParticipantId id;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne
    @MapsId("eventID")
    @JoinColumn(name = "eventID")
    private Event event;

    @Enumerated(EnumType.STRING)
    private ParticipantRole userRole = ParticipantRole.PARTICIPANT;

    private String feedback;
    private Integer rating;
    private LocalDateTime joinedAt;

    // Constructors, Getters and Setters

    public EventParticipant() {
    }

    public EventParticipantId getId() {
        return id;
    }

    public void setId(EventParticipantId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ParticipantRole getUserRole() {
        return userRole;
    }

    public void setUserRole(ParticipantRole userRole) {
        this.userRole = userRole;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}
