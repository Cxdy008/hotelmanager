package com.hotelmanager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guest")
public class GuestController {
    @GetMapping
    public ResponseEntity<String> getGuest() {
        return ResponseEntity.ok("Success");
    }
}
