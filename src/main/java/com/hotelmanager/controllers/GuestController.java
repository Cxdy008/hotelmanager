package com.hotelmanager.controllers;

import com.hotelmanager.dtos.GuestDTO;
import com.hotelmanager.models.Guest;
import com.hotelmanager.services.GuestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    // Buscar hóspede por email
    @GetMapping("/email/{email}")
    public ResponseEntity<Guest> getGuestByEmail(@PathVariable String email) {
        Guest guest = guestService.findByEmail(email);
        return ResponseEntity.ok(guest);
    }

    // Buscar hóspede por ID
    @GetMapping("/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable Integer id) {
        Guest guest = guestService.findById(id);
        return ResponseEntity.ok(guest);
    }

    // Listar todos os hóspedes (restrito a administradores)
    @GetMapping
    public ResponseEntity<List<Guest>> getAllGuests(@RequestHeader("X-Authenticated-Email") String authenticatedEmail) {
        List<Guest> guests = guestService.findAllGuests(authenticatedEmail);
        return ResponseEntity.ok(guests);
    }

    // Atualizar informações de um hóspede
    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(
            @PathVariable Integer id,
            @Valid @RequestBody GuestDTO guestDTO,
            @RequestHeader("X-Authenticated-Email") String authenticatedEmail) {
        Guest updatedGuest = guestService.updateGuest(id, guestDTO, authenticatedEmail);
        return ResponseEntity.ok(updatedGuest);
    }

    // Deletar um hóspede
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuest(
            @PathVariable Integer id,
            @RequestHeader("X-Authenticated-Email") String authenticatedEmail) {
        guestService.deleteGuest(id, authenticatedEmail);
        return ResponseEntity.noContent().build();
    }
}