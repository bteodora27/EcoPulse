package com.example.Beckend_EcoPulse.repositories;

import com.example.Beckend_EcoPulse.models.StandardUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardUserRepository extends JpaRepository<StandardUser, Long> {
    // Gata! Nu trebuie să scrii nimic altceva aici.
    // Spring Data JPA îți va da automat metoda .save()
}