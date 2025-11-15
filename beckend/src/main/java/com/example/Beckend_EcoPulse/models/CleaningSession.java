package com.example.Beckend_EcoPulse.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CleaningSessions") // Tabel nou pentru sesiuni
public class CleaningSession {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Legătura cu utilizatorul care face curățenie
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    // Starea curentă: ex: "STARTED", "COMPLETED"
    @Column(name = "session_time",nullable = false)
    private String status;

    @CreationTimestamp // Se setează automat la creare
    private LocalDateTime startTime;

    private LocalDateTime endTime; // Se setează la final

    // Vom salva numele fișierelor (simulăm salvarea pozei)
    private String beforePhotoUrl;
    private String bagsPhotoUrl;
    private String afterPhotoUrl;

    private Integer bagCount = 0; // Câți saci s-au numărat
    private Integer pointsGained = 0; // Punctele primite

    public CleaningSession() {}
}