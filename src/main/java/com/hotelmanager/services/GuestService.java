package com.hotelmanager.services;

import com.hotelmanager.dtos.GuestDTO;
import com.hotelmanager.enums.GuestRole;
import com.hotelmanager.models.Guest;
import com.hotelmanager.repositories.GuestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    @Autowired
    private GuestRepository guestRepository;

    // Buscar hóspede por email
    public Guest findByEmail(String email) {
        return guestRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Hóspede com email " + email + " não encontrado"));
    }

    // Buscar hóspede por ID

    public Guest findById(Integer id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hóspede com ID " + id + " não encontrado"));
    }

    // Listar todos os hóspedes (restrito a administradores)
    public List<Guest> findAllGuests(String authenticatedEmail) {
        // Verificar se o usuário é administrador
        Guest authenticatedGuest = findByEmail(authenticatedEmail);
        if (!authenticatedGuest.getRole().equals(GuestRole.ADMIN)) {
            throw new SecurityException("Apenas administradores podem listar todos os hóspedes");
        }
        return guestRepository.findAll();
    }

    // Atualizar informações de um hóspede
    public Guest updateGuest(Integer id, GuestDTO guestDTO, String authenticatedEmail) {
        Guest guest = findById(id);

        // Verificar se o usuário autenticado pode atualizar este hóspede
        if (!guest.getEmail().equals(authenticatedEmail) && !isAdmin(authenticatedEmail)) {
            throw new SecurityException("Usuário não autorizado para atualizar este hóspede");
        }

        // Verificar se o novo email (se alterado) já está em uso por outro hóspede
        if (!guestDTO.getEmail().equals(guest.getEmail()) && guestRepository.findByEmail(guestDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email " + guestDTO.getEmail() + " já está registrado");
        }

        // Verificar se o novo documento (se alterado) já está em uso
        if (guestDTO.getDocumentNumber() != null && !guestDTO.getDocumentNumber().equals(guest.getDocument())
                && guestRepository.existsByDocument(guestDTO.getDocumentNumber())) {
            throw new IllegalArgumentException("Documento " + guestDTO.getDocumentNumber() + " já está registrado");
        }

        // Atualizar campos
        guest.setEmail(guestDTO.getEmail().trim().toLowerCase());
        guest.setName(guestDTO.getName().trim());
        guest.setPhone(guestDTO.getPhone() != null ? guestDTO.getPhone().trim() : null);
        guest.setDocument(guestDTO.getDocumentNumber() != null ? guestDTO.getDocumentNumber().trim() : null);

        return guestRepository.save(guest);
    }

    // Deletar um hóspede
    public void deleteGuest(Integer id, String authenticatedEmail) {
        Guest guest = findById(id);

        // Verificar permissão (somente o próprio hóspede ou admin pode deletar)
        if (!guest.getEmail().equals(authenticatedEmail) && !isAdmin(authenticatedEmail)) {
            throw new SecurityException("Usuário não autorizado para deletar este hóspede");
        }

        // Verificar se o hóspede tem reservas ativas
        if (guestRepository.hasActiveReservations(id)) {
            throw new IllegalStateException("Hóspede possui reservas ativas e não pode ser deletado");
        }

        guestRepository.delete(guest);
    }

    // Verificar se o usuário autenticado é administrador
    private boolean isAdmin(String email) {
        Guest guest = findByEmail(email);
        return guest.getRole().equals(GuestRole.ADMIN);
    }
}
