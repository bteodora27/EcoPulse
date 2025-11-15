package com.example.Beckend_EcoPulse.requests;

import lombok.Data;

@Data
public class OrganizationSignUpRequest {

    // Câmpuri pentru tabelul 'users'
    private String email;
    private String username;
    private String passHash; // Se potrivește cu JSON-ul trimis

    // Câmpuri pentru tabelul 'organizations' (primele 6)
    private String organizationName;
    private String phone;
    private String contactEmail;
    private String description;
    private String website;
    private String adress; // (Numele câmpului din UML)
}