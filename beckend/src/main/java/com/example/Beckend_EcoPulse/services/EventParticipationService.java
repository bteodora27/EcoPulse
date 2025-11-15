package com.example.Beckend_EcoPulse.services;

import com.example.Beckend_EcoPulse.models.Event;
import com.example.Beckend_EcoPulse.models.EventParticipant;
import com.example.Beckend_EcoPulse.models.EventParticipantId;
import com.example.Beckend_EcoPulse.models.User;
import com.example.Beckend_EcoPulse.repositories.EventParticipantsRepository;
import com.example.Beckend_EcoPulse.repositories.EventRepository;
import com.example.Beckend_EcoPulse.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class EventParticipationService {

    private final EventParticipantsRepository participantRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventParticipationService(EventParticipantsRepository participantRepository,
                                     EventRepository eventRepository,
                                     UserRepository userRepository) {
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public void joinEvent(Long eventID, Long userID) {
        if (participantRepository.existsByEvent_EventIDAndUser_UserID(eventID, userID)) {
            throw new RuntimeException("User is already joined to the event");
        }

        Event event = eventRepository.findById(eventID)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        EventParticipant participant = new EventParticipant();
        participant.setId(new EventParticipantId(userID, eventID));
        participant.setEvent(event);
        participant.setUser(user);
        participant.setUserRole(com.example.Beckend_EcoPulse.models.enums.ParticipantRole.PARTICIPANT);
        participant.setJoinedAt(LocalDateTime.now());

        participantRepository.save(participant);
    }
}
