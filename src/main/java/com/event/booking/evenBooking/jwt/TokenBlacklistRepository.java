package com.event.booking.evenBooking.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist,String> {
    Optional<TokenBlacklist> findByToken(String token);
}
