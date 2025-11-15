package com.example.Beckend_EcoPulse.services;

import com.example.Beckend_EcoPulse.models.StandardUser;
import com.example.Beckend_EcoPulse.models.User;
import com.example.Beckend_EcoPulse.repositories.StandardUserRepository;
import com.example.Beckend_EcoPulse.repositories.UserRepository;
import com.example.Beckend_EcoPulse.requests.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StandardUserRepository standardUserRepository; // NOU: Repository-ul pentru copil

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @Transactional asigură că ambele salvări (save) reușesc,
     * sau ambele eșuează (rollback).
     */
    @Transactional
    public User registerStandardUser(SignUpRequest request) {

        // 1. Verifică duplicatele
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Error: Username is already in use!");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // 2. Creează și Salvează Părintele (User)
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setUsername(request.getUsername());

        // --- AICI ESTE CORECȚIA ---
        // Folosește .getPassHash() pentru a se potrivi cu DTO-ul tău
        newUser.setPassHash(passwordEncoder.encode(request.getPassHash()));

        newUser.setUserType("standard");

        User savedUser = userRepository.save(newUser);

        // 3. Creează și Salvează Copilul (StandardUser)
        StandardUser standardUser = new StandardUser();
        standardUser.setUser(savedUser);
        standardUser.setFirstName(request.getFirstName());
        standardUser.setLastName(request.getLastName());
        standardUser.setPhone(request.getPhone());

        // --- AICI ESTE A DOUA CORECȚIE (pe care ai făcut-o deja) ---
        LocalDate dataNasterii = LocalDate.parse(request.getBirthDate(), DateTimeFormatter.ISO_LOCAL_DATE);
        standardUser.setBirthDate(dataNasterii);

        standardUserRepository.save(standardUser);

        return savedUser;
    }
}