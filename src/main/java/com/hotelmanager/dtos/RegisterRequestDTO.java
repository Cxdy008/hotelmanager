package com.hotelmanager.dtos;

public record RegisterRequestDTO(String name, String email, String phone, String document, String password, String role) { }
