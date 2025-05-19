package com.hotelmanager.dtos;

import com.hotelmanager.enums.RoomStatus;
import com.hotelmanager.enums.RoomType;

import java.util.UUID;

public record GuestDTO(String firstName, String lastName, RoomType roomType, RoomStatus roomStatus, UUID uuid ) {

}
