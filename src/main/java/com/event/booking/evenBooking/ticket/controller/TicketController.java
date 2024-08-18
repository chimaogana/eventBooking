package com.event.booking.evenBooking.ticket.controller;

import com.event.booking.evenBooking.jwt.JwtUtil;
import com.event.booking.evenBooking.ticket.dto.TicketDto;
import com.event.booking.evenBooking.ticket.model.Ticket;
import com.event.booking.evenBooking.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<TicketDto> bookTicket(@RequestBody TicketDto ticketDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.create(ticketDto));
    }

    @DeleteMapping("/cancel/{id}")
    public void deleteById(@PathVariable("id") Long id){
        ticketService.deleteTicket(id);
    }

    @GetMapping("/user/ticket")
    public ResponseEntity<Ticket> getUserTicket(@RequestHeader("Authorization") String authorization){
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token");
        }
        String token = authorization.substring(7);
        String userId = jwtUtil.extractUserId(token);
        return ResponseEntity.status(HttpStatus.FOUND).body(ticketService.getUserTicket(userId));
    }
}
