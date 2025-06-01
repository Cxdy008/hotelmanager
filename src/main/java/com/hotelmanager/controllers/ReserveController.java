package com.hotelmanager.controllers;

import com.hotelmanager.dtos.ReserveDTO;
import com.hotelmanager.enums.ReservationStatus;
import com.hotelmanager.models.Reserve;
import com.hotelmanager.services.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/reserves")
public class ReserveController {
    @Autowired
    private ReserveService reserveService;

    // Criar uma nova reserva
    @PostMapping
    public ResponseEntity<Reserve> createReserve(@RequestBody ReserveDTO reserveDTO, Authentication authentication) {
        String guestEmail = authentication.getName(); // Email extraído do token JWT
        Reserve reserve = reserveService.createReserve(reserveDTO, guestEmail);
        return ResponseEntity.ok(reserve);
    }

    // Buscar reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserve> getReserveById(@PathVariable UUID id) {
        Reserve reserve = reserveService.findById(id);
        return ResponseEntity.ok(reserve);
    }

    // Listar reservas do hóspede autenticado
    @GetMapping("/my-reserves")
    public ResponseEntity<List<Reserve>> getMyReserves(Authentication authentication) {
        String guestEmail = authentication.getName();
        List<Reserve> reserves = reserveService.findByGuestEmail(guestEmail);
        return ResponseEntity.ok(reserves);
    }

    // Listar reservas por status (restrito a administradores, pode ser ajustado com @PreAuthorize)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Reserve>> getReservesByStatus(@PathVariable ReservationStatus status) {
        List<Reserve> reserves = reserveService.findByStatus(status);
        return ResponseEntity.ok(reserves);
    }

    // Atualizar uma reserva
    @PutMapping("/{id}")
    public ResponseEntity<Reserve> updateReserve(@PathVariable UUID id, @RequestBody ReserveDTO reserveDTO, Authentication authentication) {
        String guestEmail = authentication.getName();
        Reserve updatedReserve = reserveService.updateReserve(id, reserveDTO, guestEmail);
        return ResponseEntity.ok(updatedReserve);
    }

    // Cancelar uma reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Reserve> cancelReserve(@PathVariable UUID id, Authentication authentication) {
        String guestEmail = authentication.getName();
        Reserve canceledReserve = reserveService.cancelReserve(id, guestEmail);
        return ResponseEntity.ok(canceledReserve);
    }

    // Estatísticas de reservas (restrito a administradores, pode ser ajustado com @PreAuthorize)
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Long>> getReservationStatistics() {
        Map<String, Long> stats = reserveService.getReservationStatistics();
        return ResponseEntity.ok(stats);
    }
}
