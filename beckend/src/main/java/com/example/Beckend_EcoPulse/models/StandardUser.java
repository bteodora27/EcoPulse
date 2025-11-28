package com.example.Beckend_EcoPulse.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "StandardUsers")
public class StandardUser {

    @Id // Aceasta ESTE cheia primară
    @Column(name = "userID")
    private Long id; // NU are @GeneratedValue. ID-ul este preluat de la 'User
    @OneToOne
    @MapsId
    @JoinColumn(name = "userID")
    private User user;
    @Column(name = "standardUserRank")
    private String standardUserRank;

    @Column(name = "totalEventsJoined")
    private Integer totalEventsJoined;

    @Column(name = "totalPoints")
    private Integer totalPoints;

    @Column(name = "canCreateEvents")
    private Boolean canCreateEvents;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate birthDate;

    // ... poți adăuga și restul câmpurilor (rank, points) ...

    public StandardUser() {} // Constructor gol
}