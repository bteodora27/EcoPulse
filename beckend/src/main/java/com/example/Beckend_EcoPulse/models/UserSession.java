package com.example.Beckend_EcoPulse.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "UsersSessions") // Numele exact al tabelului tău
public class UserSession {

    @Id
    @Column(name = "sessionID") // Cheia ta primară
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Relația cu User (Cheia Străină) ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", nullable = false) // Numele coloanei de legătură
    private User user; // Obiectul părinte

    // --- Token-urile ---
    @Column(length = 500) // varchar(500)
    private String accessToken;

    @Column(length = 500)
    private String refreshToken;

    // --- Detaliile Sesiunii (opționale deocamdată) ---
    @Column(name = "ipAdress", length = 45)
    private String ipAddress;

    @Column(length = 255)
    private String deviceInfo;

    @Column(columnDefinition = "text")
    private String userAgent;

    // --- Timpii ---
    @Column(name = "createdAt", insertable = false, updatable = false) // Numele coloanei e 'createdAt'
    // 'insertable = false' îi spune lui Spring: "NU trimite acest câmp la INSERT"
    // 'updatable = false' îi spune lui Spring: "NU trimite acest câmp la UPDATE"
    private LocalDateTime createdAt;

    private LocalDateTime lastActivity;
    private LocalDateTime expiresAt;

    public UserSession() {} // Constructor gol
}