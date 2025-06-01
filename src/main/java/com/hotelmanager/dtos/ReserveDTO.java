package com.hotelmanager.dtos;

import java.time.LocalDate;

public record ReserveDTO(int roomNumber, LocalDate checkIn, LocalDate checkOut) {
}
