package com.hotelmanager.controllers;

import com.hotelmanager.dtos.LoginRequestDTO;
import com.hotelmanager.dtos.RegisterRequestDTO;
import com.hotelmanager.dtos.ResponseDTO;
import com.hotelmanager.enums.GuestRole;
import com.hotelmanager.models.Guest;
import com.hotelmanager.repositories.GuestRepository;
import com.hotelmanager.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final GuestRepository guestRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO data) {
        Guest guest = this.guestRepository.findByEmail(data.email()).orElseThrow(() -> new RuntimeException("Email not found"));
        if(passwordEncoder.matches(data.password(), guest.getPassword())){
            String token = tokenService.generateToken(guest);
            return ResponseEntity.ok(new ResponseDTO(guest.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO data) {
        Optional<Guest> guest = this.guestRepository.findByEmail(data.email());

        if(guest.isEmpty()){
            Guest newGuest = new Guest();
            newGuest.setName(data.email());
            newGuest.setEmail(data.email());
            newGuest.setPhone(data.phone());
            newGuest.setDocument(data.document());
            newGuest.setPassword(passwordEncoder.encode(data.password()));
            newGuest.setRole(GuestRole.valueOf(data.role()));
            this.guestRepository.save(newGuest);

            String token = this.tokenService.generateToken(newGuest);
            return ResponseEntity.ok(new ResponseDTO(newGuest.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
