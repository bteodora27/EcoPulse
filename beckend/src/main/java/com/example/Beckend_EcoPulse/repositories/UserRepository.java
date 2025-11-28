package com.example.Beckend_EcoPulse.repositories;

import com.example.Beckend_EcoPulse.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Această metodă este pentru verificarea email-ului
    Optional<User> findByEmail(String email);

    // NOU: Această metodă este pentru verificarea username-ului
    Optional<User> findByUsername(String username);
}