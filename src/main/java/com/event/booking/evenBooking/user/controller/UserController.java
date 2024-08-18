package com.event.booking.evenBooking.user.controller;

import com.event.booking.evenBooking.jwt.TokenBlacklist;
import com.event.booking.evenBooking.jwt.TokenBlacklistRepository;
import com.event.booking.evenBooking.user.User;
import com.event.booking.evenBooking.user.dto.AuthenticationDto;
import com.event.booking.evenBooking.user.dto.AuthenticationResponseDto;
import com.event.booking.evenBooking.jwt.JwtUtil;
import com.event.booking.evenBooking.user.service.UserInfoDetailService;
import com.event.booking.evenBooking.user.service.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/authenticate")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoDetailService userInfoDetailService;

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Autowired
    private JwtUtil javaUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    @RateLimiter(name = "basic")
    public ResponseEntity<?> login(@RequestBody AuthenticationDto authenticationDto) throws Exception{
      try{
          authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(),authenticationDto.getPassword())

       );
      }catch (BadCredentialsException e){
          throw new Exception("Incorrect Password", e);
      }
      final UserDetails userDetails = userInfoDetailService.loadUserByUsername(authenticationDto.getUsername());
      final String token = javaUtil.generateToken(userDetails);
      return ResponseEntity.ok(new AuthenticationResponseDto(token));

    }

    @PostMapping("/logout")
    public ResponseEntity<String>logout(@RequestHeader("Authorization") String authorization, Authentication authentication){
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token");
        }
        String token = authorization.substring(7);
        TokenBlacklist blacklistedToken = new TokenBlacklist();
        blacklistedToken.setToken(token);
        blacklistedToken.setExpiryDate(LocalDateTime.now().plusHours(1)); // Set appropriate expiry
        tokenBlacklistRepository.save(blacklistedToken);

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logout successful");
    }



}
