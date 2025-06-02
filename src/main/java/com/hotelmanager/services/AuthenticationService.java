package com.hotelmanager.services;

import com.hotelmanager.dtos.LoginRequestDTO;
import com.hotelmanager.dtos.RegisterRequestDTO;
import com.hotelmanager.dtos.AuthResponseDTO;
import com.hotelmanager.models.Guest;
import com.hotelmanager.enums.GuestRole;
import com.hotelmanager.exceptions.*;
import com.hotelmanager.repositories.GuestRepository;
import com.hotelmanager.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService {
    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Transactional(readOnly = true)
    public AuthResponseDTO login(LoginRequestDTO data) {
        // Validações de entrada
        validateLoginData(data);

        // Buscar usuário
        Guest guest = guestRepository.findByEmail(data.email())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + data.email()));

        // Verificar senha
        if (!passwordEncoder.matches(data.password(), guest.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        // Gerar token
        String token = tokenService.generateToken(guest);

        return new AuthResponseDTO(guest.getName(), token);
    }

    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO data) {
        // Validações de entrada
        validateRegisterData(data);

        // Verificar se usuário já existe
        if (guestRepository.findByEmail(data.email()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + data.email());
        }

        // Verificar se documento já existe (se aplicável)
        if (data.document() != null && !data.document().trim().isEmpty()) {
            if (guestRepository.existsByDocument(data.document())) {
                throw new UserAlreadyExistsException("User already exists with document: " + data.document());
            }
        }

        try {
            // Criar novo usuário
            Guest newGuest = createGuestFromDTO(data);

            // Salvar no banco
            Guest savedGuest = guestRepository.save(newGuest);

            // Gerar token
            String token = tokenService.generateToken(savedGuest);

            return new AuthResponseDTO(savedGuest.getName(), token);

        } catch (Exception e) {
            throw new AuthenticationException("Error creating user: " + e.getMessage());
        }
    }

    private void validateLoginData(LoginRequestDTO data) {
        if (data == null) {
            throw new ValidationException("Login data cannot be null");
        }
        if (data.email() == null || data.email().trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }
        if (data.password() == null || data.password().trim().isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (!isValidEmail(data.email())) {
            throw new ValidationException("Invalid email format");
        }
    }

    private void validateRegisterData(RegisterRequestDTO data) {
        if (data == null) {
            throw new ValidationException("Register data cannot be null");
        }
        if (data.name() == null || data.name().trim().isEmpty()) {
            throw new ValidationException("Name is required");
        }
        if (data.email() == null || data.email().trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }
        if (data.password() == null || data.password().trim().isEmpty()) {
            throw new ValidationException("Password is required");
        }
        /*
        if (data.role() == null || data.role().trim().isEmpty()) {
            throw new ValidationException("Role is required");
        }*/
        if (!isValidEmail(data.email())) {
            throw new ValidationException("Invalid email format");
        }
        if (data.password().length() < 6) {
            throw new ValidationException("Password must be at least 6 characters long");
        }
        /*
        if (!isValidRole(data.role())) {
            throw new ValidationException("Invalid role. Must be ADMIN, RECEPTION, or GUEST");
        }*/
    }

    private Guest createGuestFromDTO(RegisterRequestDTO data) {
        Guest guest = new Guest();
        guest.setName(data.name().trim());
        guest.setEmail(data.email().trim().toLowerCase());
        guest.setPhone(data.phone() != null ? data.phone().trim() : null);
        guest.setDocument(data.document() != null ? data.document().trim() : null);
        guest.setPassword(passwordEncoder.encode(data.password()));
        guest.setRole(GuestRole.GUEST);
        return guest;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private boolean isValidRole(String role) {
        try {
            GuestRole.valueOf(role.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
