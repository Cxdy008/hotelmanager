package com.hotelmanager.dtos;

import com.hotelmanager.enums.RoomStatus;
import com.hotelmanager.enums.RoomType;

public record RoomDTO(
        Integer roomNumber,
        RoomStatus status,
        RoomType roomType
) {}