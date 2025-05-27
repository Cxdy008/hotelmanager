package com.hotelmanager.services;

import com.hotelmanager.enums.RoomStatus;
import com.hotelmanager.enums.RoomType;
import com.hotelmanager.models.Room;
import com.hotelmanager.repositories.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    // Buscar quarto por número
    public Room findById(int roomNumber) {
        return roomRepository.findById(roomNumber)
                .orElseThrow(() -> new EntityNotFoundException("Quarto " + roomNumber + " não encontrado"));
    }

    /* Atualizar status do quarto (método principal usado pelo ReservationService)*/
    public Room updateRoomStatus(int roomNumber, RoomStatus newStatus) {
        Room room = findById(roomNumber);
        room.setStatus(newStatus);
        return roomRepository.save(room);
    }

    // Buscar todos os quartos
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Buscar quartos por status
    public List<Room> getRoomsByStatus(RoomStatus status) {
        return roomRepository.findByStatus(status);
    }

    // Buscar quartos disponíveis
    public List<Room> getAvailableRooms() {
        return roomRepository.findByStatus(RoomStatus.DISPOSED);
    }

    // Buscar quartos por tipo
    public List<Room> getRoomsByType(RoomType roomType) {
        return roomRepository.findByRoomType(roomType);
    }

    // Buscar quartos disponíveis por tipo
    public List<Room> getAvailableRoomsByType(RoomType roomType) {
        return roomRepository.findAvailableRoomsByType(roomType);
    }

    // Colocar quarto em manutenção
    public Room setRoomMaintenance(int roomNumber) {
        return updateRoomStatus(roomNumber, RoomStatus.UNDER_MAINTANCE);
    }

    // Liberar quarto (tornar disponível)
    public Room releaseRoom(int roomNumber) {
        return updateRoomStatus(roomNumber, RoomStatus.DISPOSED);
    }

    // Estatísticas dos quartos
    public Map<RoomStatus, Long> getRoomStatusStatistics() {
        Map<RoomStatus, Long> stats = new HashMap<>();

        for (RoomStatus status : RoomStatus.values()) {
            Long count = roomRepository.countByStatus(status);
            stats.put(status, count);
        }

        return stats;
    }

    // Verificar se quarto existe
    public boolean roomExists(int roomNumber) {
        return roomRepository.existsById(roomNumber);
    }
}
