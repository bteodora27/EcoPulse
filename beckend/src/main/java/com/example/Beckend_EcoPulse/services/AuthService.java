package com.example.Beckend_EcoPulse.services;

import com.example.Beckend_EcoPulse.models.Organization;
import com.example.Beckend_EcoPulse.models.StandardUser;
import com.example.Beckend_EcoPulse.models.User;
import com.example.Beckend_EcoPulse.models.UserSession;
import com.example.Beckend_EcoPulse.repositories.OrganizationRepository;
import com.example.Beckend_EcoPulse.repositories.StandardUserRepository;
import com.example.Beckend_EcoPulse.repositories.UserRepository;
import com.example.Beckend_EcoPulse.repositories.UserSessionRepository;
import com.example.Beckend_EcoPulse.requests.LoginRequest;
import com.example.Beckend_EcoPulse.requests.OrganizationSignUpRequest;
import com.example.Beckend_EcoPulse.requests.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private StandardUserRepository standardUserRepository; // NOU: Repository-ul pentru copil

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrganizationRepository organizationRepository;

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

    @Transactional
    public String loginUser(LoginRequest loginRequest) {

        // 1. Găsește user-ul după email
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Credentiale invalide.");
        }
        User user = userOptional.get();

        // 2. Verifică parola
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassHash())) {
            throw new RuntimeException("Credentiale invalide.");
        }

        // 3. Creează Token-uri
        // (Folosim UUID-uri simple, deoarece "inteligența" stă în baza de date)
        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString(); // Pentru reînnoire

        // 4. Creează și Salvează Sesiunea
        UserSession session = new UserSession();
        session.setUser(user); // Setează legătura (cheia străină userID)
        session.setAccessToken(accessToken);
        session.setRefreshToken(refreshToken);
        session.setExpiresAt(LocalDateTime.now().plusHours(8)); // Setează expirarea (ex: 8 ore)
        // (createdAt se pune automat de @CreationTimestamp)

        // TODO: Poți adăuga ipAddress și deviceInfo dacă le primești de la Android

        userSessionRepository.save(session);

        // 5. Returnează doar Access Token-ul
        return accessToken;
    }
    @Transactional
    public User registerOrganization(OrganizationSignUpRequest request) {

        // 1. Verifică duplicatele (în tabelul 'users')
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
        newUser.setPassHash(passwordEncoder.encode(request.getPassHash()));
        newUser.setUserType("organization"); // Setezi tipul corect!
        // userCreationTime se setează automat de @CreationTimestamp

        User savedUser = userRepository.save(newUser);

        // 3. Creează și Salvează Copilul (Organization)
        Organization organization = new Organization();
        organization.setUser(savedUser); // Setează relația și ID-ul (@MapsId)

        // Setează datele specifice organizației
        organization.setOrganizationName(request.getOrganizationName());
        organization.setPhone(request.getPhone());
        organization.setContactEmail(request.getContactEmail());
        organization.setDescription(request.getDescription());
        String websiteUrl = request.getWebsite();
        if (websiteUrl != null && !websiteUrl.isEmpty() &&
                !websiteUrl.startsWith("http://") && !websiteUrl.startsWith("https://"))
        {
            // Dacă nu începe cu http, îl adăugăm noi automat
            websiteUrl = "http://" + websiteUrl;
        }
        organization.setWebsite(websiteUrl);
        organization.setAdress(request.getAdress());

        organizationRepository.save(organization);

        return savedUser;
    }
}