package com.hotelmanager.repositories;

import com.hotelmanager.enums.RoomStatus;
import com.hotelmanager.enums.RoomType;
import com.hotelmanager.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findByStatus(RoomStatus status);

    List<Room> findByType(RoomType roomType);

    List<Room> findByStatusAndType(RoomStatus status, RoomType type);

    @Query("SELECT r FROM Room r WHERE r.status = 'AVAILABLE' AND r.type = :roomType")
    List<Room> findAvailableRoomsByType(@Param("roomType") RoomType roomType);

    @Query("SELECT COUNT(r) FROM Room r WHERE r.status = :status")
    Long countByStatus(@Param("status") RoomStatus status);
}
