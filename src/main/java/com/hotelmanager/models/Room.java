package com.hotelmanager.models;


import jakarta.persistence.*;
import lombok.Data;
import com.hotelmanager.enums.RoomType;
import com.hotelmanager.enums.RoomStatus;

@Data
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    private int number;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", length = 20)
    private RoomType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RoomStatus status;
}
