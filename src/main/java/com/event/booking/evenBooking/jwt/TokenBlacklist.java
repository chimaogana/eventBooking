package com.event.booking.evenBooking.jwt;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_blacklist")
@Getter
@Setter
public class TokenBlacklist {
    @Id
    private String token;
    private LocalDateTime expiryDate;
}
