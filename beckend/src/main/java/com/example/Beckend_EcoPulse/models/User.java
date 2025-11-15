package com.example.Beckend_EcoPulse.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "userID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    // --- CORECȚIA CRITICĂ ---
    @Column(name = "passHash", nullable = false)
    private String passHash;

    @Column(name = "userType")
    private String userType;

    @CreationTimestamp
    @Column(name = "userCreationTime")
    private LocalDateTime userCreationTime;

    @Column(name = "lastLogin")
    private LocalDateTime lastLogin;

    @Column(name = "loginCount")
    private Integer loginCount;

    @Column(name = "failedLoginAttempts")
    private Integer failedLoginAttempts;

    @Column(name = "passwordChangedAt")
    private LocalDateTime passwordChangedAt;

    // Constructor gol (necesar)
    public User() {}


}