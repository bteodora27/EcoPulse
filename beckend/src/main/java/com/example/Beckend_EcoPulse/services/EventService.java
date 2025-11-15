//package com.example.Beckend_EcoPulse.services;
//
//import com.example.Beckend_EcoPulse.models.enums.UserType;
//import com.example.Beckend_EcoPulse.requests.CreateEventRequest;
//import com.example.Beckend_EcoPulse.models.*;
//import com.example.Beckend_EcoPulse.models.enums.ParticipantRole;
//import com.example.Beckend_EcoPulse.repositories.*;
//import com.example.Beckend_EcoPulse.exception.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
//@Service
//@Transactional
//public class EventService {
//
//    @Autowired
//    private EventRepository eventRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private StandardUserRepository standardUserRepository;
//
//    @Autowired
//    private OrganisationUserRepository organisationRepository;
//
//    @Autowired
//    private GoogleMapsService googleMapsService;
//
//    public Event createEvent(CreateEventRequest request, Long creatorID) {
//        User creator = userRepository.findById(creatorID)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//        validateEventCreationPermission(creator);
//
//        Event event = new Event();
//        event.setEventName(request.getEventName());
//        event.setDescription(request.getDescription());
//        event.setStartTime(request.getStartTime());
//        event.setEndTime(request.getEndTime());
//        event.setLocation(request.getLocation());
//        event.setCreator(creator);
//        event.setVisibility(request.getVisibility());
//        event.setCreationTime(LocalDateTime.now());
//
//        return eventRepository.save(event);
//    }
//
//    private void validateEventCreationPermission(User creator) {
//        if (Objects.equals(creator.getUserType(), UserType.STANDARD.toString())) {
//            StandardUser standardUser =standardUserRepository.findByUser(creator)
//                    .orElseThrow(() -> new InsufficientPermissionsException("Standard user data missing"));
//            if (!standardUser.getCanCreateEvent()) {
//                throw new InsufficientPermissionsException(
//                        "Only standard users with create permission allowed"
//                );
//            }
//        }
//        else if (Objects.equals(creator.getUserType(), UserType.ORGANIZATION.toString())) {
//            Organization org = organisationRepository.findByUser(creator)
//                    .orElseThrow(() -> new InsufficientPermissionsException("Organization data missing"));
//            if (!org.getCanCreateEvents()) {
//                throw new InsufficientPermissionsException(
//                        "Organization is not authorized to create events"
//                );
//            }
//        }
//        else {
//            throw new InsufficientPermissionsException("User has no permission to create events");
//        }
//    }
//
//
//
//    public List<Event> getUpcomingEvents() {
//        return eventRepository.findUpcomingEvents(LocalDateTime.now());
//    }
//
//    public Event getEventById(Long eventID) {
//        return eventRepository.findById(eventID)
//                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
//    }
//}
