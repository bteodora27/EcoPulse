package com.example.Beckend_EcoPulse.repositories;

import com.example.Beckend_EcoPulse.models.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    // Nu e nevoie să adaugi nimic aici, .save() este de ajuns
}