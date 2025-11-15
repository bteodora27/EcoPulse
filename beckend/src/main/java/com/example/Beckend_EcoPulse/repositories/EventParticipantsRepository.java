package com.example.Beckend_EcoPulse.repositories;

import com.example.Beckend_EcoPulse.models.EventParticipant;
import com.example.Beckend_EcoPulse.models.EventParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventParticipantsRepository extends JpaRepository<EventParticipant, EventParticipantId> {

    List<EventParticipant> findByEvent_EventID(Long eventID);

    List<EventParticipant> findByUser_UserID(Long userID);

    Optional<EventParticipant> findByEvent_EventIDAndUser_UserID(Long eventID, Long userID);

    boolean existsByEvent_EventIDAndUser_UserID(Long eventID, Long userID);

    @Query("SELECT COUNT(ep) FROM EventParticipant ep WHERE ep.event.eventID = :eventID")
    int countParticipantsByEventId(@Param("eventID") Long eventID);
}
