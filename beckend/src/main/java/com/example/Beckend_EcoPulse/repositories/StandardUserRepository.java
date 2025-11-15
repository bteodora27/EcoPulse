package com.example.Beckend_EcoPulse.repositories;

import com.example.Beckend_EcoPulse.models.StandardUser;
import com.example.Beckend_EcoPulse.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StandardUserRepository extends JpaRepository<StandardUser, Long> {
    // Gata! Nu trebuie să scrii nimic altceva aici.
    // Spring Data JPA îți va da automat metoda .save()
    Optional<StandardUser> findByUser(User user);
}