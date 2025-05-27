package com.hotelmanager.controllers;

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
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{roomNumber}")
    public ResponseEntity<Room> getRoomById(@PathVariable int roomNumber) {
        try {
            Room room = roomService.findById(roomNumber);
            return ResponseEntity.ok(room);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{roomNumber}/status")
    public ResponseEntity<Room> updateRoomStatus(@PathVariable int roomNumber,
                                                 @RequestBody Map<String, String> statusUpdate) {
        try {
            RoomStatus newStatus = RoomStatus.valueOf(statusUpdate.get("status"));
            Room updated = roomService.updateRoomStatus(roomNumber, newStatus);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms() {
        return ResponseEntity.ok(roomService.getAvailableRooms());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Room>> getRoomsByStatus(@PathVariable RoomStatus status) {
        return ResponseEntity.ok(roomService.getRoomsByStatus(status));
    }

    @GetMapping("/type/{roomType}")
    public ResponseEntity<List<Room>> getRoomsByType(@PathVariable RoomType roomType) {
        return ResponseEntity.ok(roomService.getRoomsByType(roomType));
    }

    @GetMapping("/available/type/{roomType}")
    public ResponseEntity<List<Room>> getAvailableRoomsByType(@PathVariable RoomType roomType) {
        return ResponseEntity.ok(roomService.getAvailableRoomsByType(roomType));
    }

    @PostMapping("/{roomNumber}/maintenance")
    public ResponseEntity<Room> setRoomMaintenance(@PathVariable int roomNumber) {
        try {
            Room updated = roomService.setRoomMaintenance(roomNumber);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{roomNumber}/release")
    public ResponseEntity<Room> releaseRoom(@PathVariable int roomNumber) {
        try {
            Room updated = roomService.releaseRoom(roomNumber);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<RoomStatus, Long>> getRoomStatistics() {
        return ResponseEntity.ok(roomService.getRoomStatusStatistics());
    }
}
