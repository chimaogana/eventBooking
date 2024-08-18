package com.event.booking.evenBooking.event.controller;

import com.event.booking.evenBooking.event.dto.EventDto;
import com.event.booking.evenBooking.event.model.Event;
import com.event.booking.evenBooking.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    EventService eventService;



    @GetMapping("/all")
    public ResponseEntity<List<EventDto>> getAllEvents() {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.FOUND).body(eventService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<EventDto> create(@RequestBody EventDto eventDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.create(eventDto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<EventDto> update(@PathVariable("id") Long id, @RequestBody EventDto eventDto ){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(eventService.update(id,eventDto));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id){
        eventService.delete(id);
    }




}
