package com.hotelmanager.dtos;

import lombok.Getter;

public record GuestDTO(String email, String firstName, String lastName, String phone, String documentNumber) {
    public String getEmail() {
        return email;
    }


    public String getDocumentNumber() {
        return documentNumber;
    }


    public String getName() {
        return String.format("%s  %s", firstName, lastName);
    }

    public String getPhone() {
        return phone;
    }
}
