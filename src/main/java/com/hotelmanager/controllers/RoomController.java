package com.hotelmanager.controllers;

import com.hotelmanager.dtos.RoomDTO;
import com.hotelmanager.enums.RoomStatus;
import com.hotelmanager.enums.RoomType;
import com.hotelmanager.models.Room;
import com.hotelmanager.services.RoomService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms().stream().map(this::toRoomDTO).toList());
    }

    @GetMapping("/{roomNumber}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable int roomNumber) {
        try {
            Room room = roomService.findById(roomNumber);
            return ResponseEntity.ok(toRoomDTO(room));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{roomNumber}/status")
    public ResponseEntity<RoomDTO> updateRoomStatus(@PathVariable int roomNumber,
                                                    @RequestBody RoomDTO roomDTO) {
        try {
            RoomStatus newStatus = roomDTO.status();
            if (newStatus == null) {
                return ResponseEntity.badRequest().build();
            }
            Room updated = roomService.updateRoomStatus(roomNumber, newStatus);
            return ResponseEntity.ok(toRoomDTO(updated));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<RoomDTO>> getAvailableRooms() {
        return ResponseEntity.ok(roomService.getAvailableRooms().stream().map(this::toRoomDTO).toList());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RoomDTO>> getRoomsByStatus(@PathVariable RoomStatus status) {
        return ResponseEntity.ok(roomService.getRoomsByStatus(status).stream().map(this::toRoomDTO).toList());
    }

    @GetMapping("/type/{roomType}")
    public ResponseEntity<List<RoomDTO>> getRoomsByType(@PathVariable RoomType roomType) {
        return ResponseEntity.ok(roomService.getRoomsByType(roomType).stream().map(this::toRoomDTO).toList());
    }

    @GetMapping("/available/type/{roomType}")
    public ResponseEntity<List<RoomDTO>> getAvailableRoomsByType(@PathVariable RoomType roomType) {
        return ResponseEntity.ok(roomService.getAvailableRoomsByType(roomType).stream().map(this::toRoomDTO).toList());
    }

    @PostMapping("/{roomNumber}/maintenance")
    public ResponseEntity<RoomDTO> setRoomMaintenance(@PathVariable int roomNumber) {
        try {
            Room updated = roomService.setRoomMaintenance(roomNumber);
            return ResponseEntity.ok(toRoomDTO(updated));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{roomNumber}/release")
    public ResponseEntity<RoomDTO> setRoomRelease(@PathVariable int roomNumber) {
        try {
            Room updated = roomService.releaseRoom(roomNumber);
            return ResponseEntity.ok(toRoomDTO(updated));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<RoomStatus, Long>> getRoomStatistics() {
        return ResponseEntity.ok(roomService.getRoomStatusStatistics());
    }

    private RoomDTO toRoomDTO(Room room) {
        return new RoomDTO(room.getNumber(), room.getStatus(), room.getType());
    }
}