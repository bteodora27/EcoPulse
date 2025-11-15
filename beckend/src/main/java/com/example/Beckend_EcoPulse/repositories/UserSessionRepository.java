package com.example.Beckend_EcoPulse.repositories;

import com.example.Beckend_EcoPulse.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    // Deocamdată nu e nevoie de metode custom, .save() este de ajuns
}