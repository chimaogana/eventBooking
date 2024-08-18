package com.event.booking.evenBooking.event.service;

import com.event.booking.evenBooking.event.dto.EventDto;
import com.event.booking.evenBooking.event.model.Event;
import com.event.booking.evenBooking.event.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    EventRepository eventRepository;
    public List<EventDto> findAll() {
        return eventRepository.findAll()
                .stream().map(event->toDTO(event)).toList();
    }

    public EventDto findById(Long id){
        Optional<Event> eventOptional = eventRepository.findById(id);
        Event event = eventOptional.orElseThrow(()->new RuntimeException("NOT FOUND" + id));
        return toDTO(event);
    }

    public EventDto update(Long id, EventDto eventDto){
     Optional<Event> eventOptional = eventRepository.findById(id);
     if(eventOptional.isPresent()){
         Event event = eventOptional.orElseThrow(()-> new RuntimeException("NOT FOUND" + id));
         if(Objects.nonNull(event.getDescription())){
             event.setDescription(eventDto.getDescription());
         }
         if(Objects.nonNull(event.getName())){
             event.setName(eventDto.getName());
         }
         if(Objects.nonNull(event.getDate())){
             event.setDate(eventDto.getDate());
         }
         return toDTO(eventRepository.save(event));

     }
     return null;
    }


    public void delete(Long id) {
        eventRepository.deleteById(id);
    }


    public EventDto create(EventDto eventDto){

        return toDTO(eventRepository.save(toEntity(eventDto)));
    }

    public EventDto toDTO(Event entity){
        return EventDto.builder().name(entity.getName())
                .description(entity.getDescription()).build();
    }

    public Event toEntity(EventDto dto){
        return Event.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
