package com.event.booking.evenBooking.ticket.service;

import com.event.booking.evenBooking.ticket.dto.TicketDto;
import com.event.booking.evenBooking.ticket.model.Ticket;
import com.event.booking.evenBooking.ticket.repository.TicketRepository;
import com.event.booking.evenBooking.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;

    public TicketDto create(TicketDto ticketDto){
      return toDto(ticketRepository.save(toEntity(ticketDto)));
    }

    public void deleteTicket(Long id){
        ticketRepository.deleteById(id);
    }

    public Ticket getUserTicket(String userId){
        return ticketRepository.findTicketByUserId(userId);
    }

    public Ticket toEntity(TicketDto ticketDto){
        return Ticket.builder()
                        .eventId(ticketDto.getEventId())
                                .userId(ticketDto.getUserId()).build();

    }

    public TicketDto toDto(Ticket ticket){
        return TicketDto.builder().eventId(ticket.getEventId()).userId(ticket.getUserId()).build();
    }
}
