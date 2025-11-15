package com.example.Beckend_EcoPulse.repositories;

import com.example.Beckend_EcoPulse.models.Event;
import com.example.Beckend_EcoPulse.models.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByStatus(EventStatus status);

    List<Event> findByCreator_UserID(Long creatorID);

    @Query("SELECT e FROM Event e WHERE e.startTime >= :now AND e.status = 'UPCOMING'")
    List<Event> findUpcomingEvents(@Param("now") LocalDateTime now);

    @Query("SELECT e FROM Event e WHERE e.visibility = 'PUBLIC' AND e.status = 'UPCOMING'")
    List<Event> findPublicUpcomingEvents();
}
