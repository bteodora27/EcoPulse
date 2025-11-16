package com.example.Beckend_EcoPulse.repositories;

import com.example.Beckend_EcoPulse.models.EventParticipant;
import com.example.Beckend_EcoPulse.models.EventParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, EventParticipantId> {
}