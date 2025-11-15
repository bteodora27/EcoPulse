package com.example.Beckend_EcoPulse.models;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EventParticipantId implements Serializable {
    private Long userID;
    private Long eventID;

    public EventParticipantId() {}

    public EventParticipantId(Long userID, Long eventID) {
        this.userID = userID;
        this.eventID = eventID;
    }

    // Getters and Setters
    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventParticipantId that = (EventParticipantId) o;
        return Objects.equals(userID, that.userID) &&
                Objects.equals(eventID, that.eventID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, eventID);
    }
}
