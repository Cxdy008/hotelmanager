package com.hotelmanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GuestRole {
    GUEST("guest"),
    RECEPTION("receptionist"),
    ADMIN("admin");

    private final String roleName;
}
