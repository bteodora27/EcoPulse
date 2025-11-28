package com.example.Beckend_EcoPulse.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventID")
    private Long id;

    @Column(nullable = false)
    private String eventName;

    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    private String visibility; // "public" sau "volunteers"

    private String status; // "upcoming", "ongoing", "completed", "cancelled"

    @Column(nullable = false)
    private int totalPoints = 0;

    @CreationTimestamp
    private LocalDateTime creationTime;

    // Relația cu User (Creatorul/Organizatorul)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creatorID", nullable = false)
    private User creator;

    public Event() {}
}