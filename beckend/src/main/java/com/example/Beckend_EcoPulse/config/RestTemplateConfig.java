package com.example.Beckend_EcoPulse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // --- ADAUGĂ ACEST BLOC ---
        // Adăugăm convertoarele necesare pentru a trimite fișiere (multipart)
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new FormHttpMessageConverter()); // Pentru multipart/form-data
        messageConverters.add(new ResourceHttpMessageConverter()); // Pentru fișiere ca resurse
        messageConverters.add(new MappingJackson2HttpMessageConverter()); // Pentru JSON (ca să nu strici celelalte apeluri)

        restTemplate.setMessageConverters(messageConverters);
        // --- SFÂRȘIT BLOC NOU ---

        return restTemplate;
    }
}