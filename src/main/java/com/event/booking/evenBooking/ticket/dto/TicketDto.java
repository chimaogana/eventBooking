package com.event.booking.evenBooking.ticket.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class TicketDto {

    private Long eventId;
    private Long userId;
    private String ticketId;

}
