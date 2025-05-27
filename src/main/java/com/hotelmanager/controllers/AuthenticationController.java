package com.hotelmanager.controllers;

import com.hotelmanager.dtos.LoginRequestDTO;
import com.hotelmanager.dtos.RegisterRequestDTO;
import com.hotelmanager.dtos.AuthResponseDTO;
import com.hotelmanager.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO data) {
        AuthResponseDTO response = authenticationService.login(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO data) {
        AuthResponseDTO response = authenticationService.register(data);
        return ResponseEntity.ok(response);
    }
}
