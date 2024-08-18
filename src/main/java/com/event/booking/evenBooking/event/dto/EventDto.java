package com.event.booking.evenBooking.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class EventDto {

    private String name;

    private String description;

    private Date date;

}
