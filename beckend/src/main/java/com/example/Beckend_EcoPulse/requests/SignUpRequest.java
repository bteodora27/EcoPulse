package com.example.Beckend_EcoPulse.requests;

import lombok.Data;
import java.time.LocalDate; // NOU: Pentru data nașterii

@Data
public class SignUpRequest {

    // Câmpuri pentru tabelul 'users'
    private String email;
    private String username;
    private String passHash; // Vei folosi 'passHash' în Postman

    // Câmpuri pentru tabelul 'standardusers'
    private String firstName;
    private String lastName;
    private String phone;
    private String birthDate;
}