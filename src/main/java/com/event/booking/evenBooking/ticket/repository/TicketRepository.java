package com.event.booking.evenBooking.ticket.repository;

import com.event.booking.evenBooking.ticket.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    Ticket findTicketByUserId(String userId);
}
