//package com.example.Beckend_EcoPulse.controllers;
//
//import com.example.Beckend_EcoPulse.requests.CreateEventRequest;
//import com.example.Beckend_EcoPulse.response.EventDTO;
//import com.example.Beckend_EcoPulse.models.Event;
//import com.example.Beckend_EcoPulse.services.EventService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.validation.Valid;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/events")
//@CrossOrigin(origins = "*")
//public class EventController {
//
//    @Autowired
//    private EventService eventService;
//
//    @PostMapping
//    public ResponseEntity<EventDTO> createEvent(
//            @Valid @RequestBody CreateEventRequest request,
//            @RequestHeader("User-ID") Long creatorID) {
//
//        Event event = eventService.createEvent(request, creatorID);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(new EventDTO(event));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<EventDTO>> getAllUpcomingEvents() {
//        List<Event> events = eventService.getUpcomingEvents();
//        List<EventDTO> eventDTOs = events.stream()
//                .map(EventDTO::new)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(eventDTOs);
//    }
//
//    @GetMapping("/{eventID}")
//    public ResponseEntity<EventDTO> getEventById(@PathVariable Long eventID) {
//        Event event = eventService.getEventById(eventID);
//        return ResponseEntity.ok(new EventDTO(event));
//    }
//}
