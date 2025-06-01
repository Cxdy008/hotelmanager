package com.hotelmanager.services;

import com.hotelmanager.dtos.ReserveDTO;
import com.hotelmanager.enums.ReservationStatus;
import com.hotelmanager.enums.RoomStatus;
import com.hotelmanager.models.Guest;
import com.hotelmanager.models.Reserve;
import com.hotelmanager.models.Room;
import com.hotelmanager.repositories.ReserveRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Transactional
@Service
public class ReserveService {

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private GuestService guestService; // Supondo que existe um serviço para gerenciar hóspedes

    // Criar uma nova reserva
    public Reserve createReserve(ReserveDTO reserveDTO, String guestEmail) {
        // Validar datas
        if (reserveDTO.checkIn().isAfter(reserveDTO.checkOut())) {
            throw new IllegalArgumentException("Data de check-in não pode ser posterior à data de check-out");
        }

        // Verificar se o quarto existe e está disponível
        Room room = roomService.findById(reserveDTO.roomNumber());
        if (room.getStatus() != RoomStatus.DISPOSED) {
            throw new IllegalStateException("Quarto " + room.getNumber() + " não está disponível");
        }

        // Verificar se o quarto está reservado no período solicitado
        if (isRoomReserved(room.getNumber(), reserveDTO.checkIn(), reserveDTO.checkOut())) {
            throw new IllegalStateException("Quarto " + room.getNumber() + " já está reservado para o período solicitado");
        }

        // Buscar hóspede pelo email do token JWT
        Guest guest = guestService.findByEmail(guestEmail);

        // Criar a reserva
        Reserve reserve = new Reserve();
        reserve.setGuest(guest);
        reserve.setRoom(room);
        reserve.setCheckin(reserveDTO.checkIn());
        reserve.setCheckout(reserveDTO.checkOut());
        reserve.setStatus(ReservationStatus.ACTIVE);

        // Atualizar status do quarto para ocupado
        roomService.updateRoomStatus(room.getNumber(), RoomStatus.RESERVED);

        return reserveRepository.save(reserve);
    }

    // Verificar se o quarto está reservado em um período
    private boolean isRoomReserved(int roomNumber, LocalDate checkin, LocalDate checkout) {
        List<Reserve> conflictingReserves = reserveRepository.findByRoomNumberAndDateRange(
                roomNumber, checkin, checkout, ReservationStatus.ACTIVE);
        return !conflictingReserves.isEmpty();
    }

    // Buscar reserva por ID
    public Reserve findById(UUID id) {
        return reserveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva " + id + " não encontrada"));
    }

    // Listar todas as reservas de um hóspede (baseado no email do token JWT)
    public List<Reserve> findByGuestEmail(String guestEmail) {
        Guest guest = guestService.findByEmail(guestEmail);
        return reserveRepository.findByGuest(guest);
    }

    // Listar reservas por status
    public List<Reserve> findByStatus(ReservationStatus status) {
        return reserveRepository.findByStatus(status);
    }

    // Cancelar uma reserva
    public Reserve cancelReserve(UUID reserveId, String guestEmail) {
        Reserve reserve = findById(reserveId);

        // Verificar se o usuário tem permissão para cancelar (baseado no email)
        if (!reserve.getGuest().getEmail().equals(guestEmail)) {
            throw new SecurityException("Usuário não autorizado para cancelar esta reserva");
        }

        // Verificar se a reserva já está cancelada
        if (reserve.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reserva já está cancelada");
        }

        // Atualizar status da reserva
        reserve.setStatus(ReservationStatus.CANCELLED);

        // Liberar o quarto
        roomService.releaseRoom(reserve.getRoom().getNumber());

        return reserveRepository.save(reserve);
    }

    // Atualizar uma reserva (exemplo: mudar datas ou quarto)
    public Reserve updateReserve(UUID reserveId, ReserveDTO reserveDTO, String guestEmail) {
        Reserve reserve = findById(reserveId);

        // Verificar permissão
        if (!reserve.getGuest().getEmail().equals(guestEmail)) {
            throw new SecurityException("Usuário não autorizado para atualizar esta reserva");
        }

        // Validar datas
        if (reserveDTO.checkIn().isAfter(reserveDTO.checkOut())) {
            throw new IllegalArgumentException("Data de check-in não pode ser posterior à data de check-out");
        }

        // Verificar se o novo quarto (se alterado) está disponível
        if (reserveDTO.roomNumber() != reserve.getRoom().getNumber()) {
            Room newRoom = roomService.findById(reserveDTO.roomNumber());
            if (newRoom.getStatus() != RoomStatus.DISPOSED) {
                throw new IllegalStateException("Quarto " + newRoom.getNumber() + " não está disponível");
            }
            if (isRoomReserved(newRoom.getNumber(), reserveDTO.checkIn(), reserveDTO.checkOut())) {
                throw new IllegalStateException("Quarto " + newRoom.getNumber() + " já está reservado para o período solicitado");
            }

            // Liberar o quarto anterior
            roomService.releaseRoom(reserve.getRoom().getNumber());
            // Atualizar para o novo quarto
            reserve.setRoom(newRoom);
            roomService.updateRoomStatus(newRoom.getNumber(), RoomStatus.RESERVED);
        }

        // Atualizar datas
        reserve.setCheckin(reserveDTO.checkIn());
        reserve.setCheckout(reserveDTO.checkOut());

        return reserveRepository.save(reserve);
    }

    // Estatísticas de reservas
    public Map<String, Long> getReservationStatistics() {
        Map<String, Long> stats = new HashMap<>();
        for (ReservationStatus status : ReservationStatus.values()) {
            Long count = reserveRepository.countByStatus(status);
            stats.put(status.name(), count);
        }
        return stats;
    }
}
