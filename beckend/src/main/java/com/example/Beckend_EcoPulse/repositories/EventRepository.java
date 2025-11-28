package com.example.Beckend_EcoPulse.repositories;

import com.example.Beckend_EcoPulse.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime; // <-- Adaugă acest import
import java.util.List;
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStatusAndEndTimeBefore(String status, LocalDateTime now);
}