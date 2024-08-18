package com.event.booking.evenBooking.event.repository;

import com.event.booking.evenBooking.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
