package com.example.Beckend_EcoPulse.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "StandardUsers")
public class StandardUser {

    @Id
    @Column(name = "userID")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userID")
    private User user;

    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate birthDate;

    // Câmp care indică dacă user-ul poate crea evenimente
    @Column(nullable = false)
    private Boolean canCreateEvent = false;

    // Constructor gol necesar pentru JPA
    public StandardUser() {}

    // Poți adăuga constructori cu parametri sau metode de business, dacă dorești


    public Boolean canCreateEvent() {
        return canCreateEvent;
    }
}
