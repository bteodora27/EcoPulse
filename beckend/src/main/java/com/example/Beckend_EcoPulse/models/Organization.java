package com.example.Beckend_EcoPulse.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Organizations") // Numele exact al tabelului tău
public class Organization {

    @Id
    @Column(name = "userID")
    private Long id; // NU are @GeneratedValue. ID-ul este preluat de la 'User'

    // --- Relația cu User (Cheia Străină) ---
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Spune-i lui JPA: "ID-ul meu ESTE ID-ul din obiectul 'user'"
    @JoinColumn(name = "userID") // Numele coloanei de legătură
    private User user;

    // --- Câmpurile din UML-ul tău ---
    private String organizationName;
    private String phone;
    private String contactEmail;
    private String description;
    private String website;
    private String adress; // (Atenție: 'adress' este scris așa în UML-ul tău)

    // (Restul câmpurilor: totalEventsCreated, etc.
    //  vor fi null/default, ceea ce e OK)

    public Organization() {} // Constructor gol
}