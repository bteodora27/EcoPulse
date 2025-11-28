package com.example.Beckend_EcoPulse.repositories;

import com.example.Beckend_EcoPulse.models.CleaningSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CleaningSessionRepository extends JpaRepository<CleaningSession, Long> {

    // O metodă custom de care vom avea nevoie:
    // Găsește o sesiune după ID, dar DOAR dacă e încă "STARTED"
    Optional<CleaningSession> findByIdAndStatus(Long id, String status);
}