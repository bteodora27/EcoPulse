package com.example.Beckend_EcoPulse.repositories;

import com.example.Beckend_EcoPulse.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}