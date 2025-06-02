package com.hotelmanager.controllers;

import com.hotelmanager.dtos.ReserveDTO;
import com.hotelmanager.enums.ReservationStatus;
import com.hotelmanager.models.Reserve;
import com.hotelmanager.services.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/reserves")
public class ReserveController {

    @Autowired
    private ReserveService reserveService;

    @PostMapping
    public ResponseEntity<Reserve> createReserve(@RequestBody ReserveDTO reserveDTO, Authentication authentication) {
        String guestEmail = authentication.getName();
        Reserve reserve = reserveService.createReserve(reserveDTO, guestEmail);
        return ResponseEntity.ok(reserve);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserve> getReserveById(@PathVariable Integer id) {
        Reserve reserve = reserveService.findById(id);
        return ResponseEntity.ok(reserve);
    }

    @GetMapping("/my-reserves")
    public ResponseEntity<List<Reserve>> getMyReserves(Authentication authentication) {
        String guestEmail = authentication.getName();
        List<Reserve> reserves = reserveService.findByGuestEmail(guestEmail);
        return ResponseEntity.ok(reserves);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Reserve>> getReservesByStatus(@PathVariable ReservationStatus status) {
        List<Reserve> reserves = reserveService.findByStatus(status);
        return ResponseEntity.ok(reserves);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserve> updateReserve(@PathVariable Integer id, @RequestBody ReserveDTO reserveDTO, Authentication authentication) {
        String guestEmail = authentication.getName();
        Reserve updatedReserve = reserveService.updateReserve(id, reserveDTO, guestEmail);
        return ResponseEntity.ok(updatedReserve);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reserve> cancelReserve(@PathVariable Integer id, Authentication authentication) {
        String guestEmail = authentication.getName();
        Reserve canceledReserve = reserveService.cancelReserve(id, guestEmail);
        return ResponseEntity.ok(canceledReserve);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getReservationStatistics() {
        Map<String, Long> stats = reserveService.getReservationStatistics();
        return ResponseEntity.ok(stats);
    }
}