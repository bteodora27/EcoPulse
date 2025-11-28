package com.example.Beckend_EcoPulse.repositories;

import com.example.Beckend_EcoPulse.models.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

}