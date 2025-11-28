package com.example.Beckend_EcoPulse.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SessionStartResponseDTO {
    @JsonProperty("sessionId")
    private Long sessionId;
    private Long userId; // Trimitem doar ID-ul utilizatorului
}