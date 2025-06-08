package com.hotelmanager.dtos;

import javax.management.relation.Role;

public record GuestResponseDTO(Integer id, String name, String email, String phoneNumber, String document, Role role) {
}
