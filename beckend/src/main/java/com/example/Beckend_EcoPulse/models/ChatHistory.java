package com.example.Beckend_EcoPulse.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity // Îi spune lui JPA că aceasta este un tabel
@Table(name = "chat_history") // Numele tabelului în MySQL
public class ChatHistory {

    @Id // Marchează câmpul 'id' ca Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Spune MySQL să genereze automat ID-ul
    private Long id;

    @Column(columnDefinition = "TEXT") // Permite text lung
    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    private LocalDateTime timestamp;

    // Constructor gol (OBLIGATORIU pentru JPA)
    public ChatHistory() {}

    // Constructor util (pentru a crea obiecte noi)
    public ChatHistory(String question, String answer, LocalDateTime timestamp) {
        this.question = question;
        this.answer = answer;
        this.timestamp = timestamp;
    }

    // Getteri și Setteri (OBLIGATORII pentru JPA)
    // (Sau poți adăuga @Getter @Setter @NoArgsConstructor @AllArgsConstructor de la Lombok)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}