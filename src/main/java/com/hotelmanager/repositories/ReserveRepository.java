package com.hotelmanager.repositories;

import com.hotelmanager.enums.ReservationStatus;
import com.hotelmanager.models.Guest;
import com.hotelmanager.models.Reserve;
import com.hotelmanager.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReserveRepository extends JpaRepository<Reserve, UUID> {
    List<Reserve> findByStatus(ReservationStatus status);

    List<Reserve> findByCheckinBetween(LocalDate startDate, LocalDate endDate);

    List<Reserve> findByCheckoutBetween(LocalDate startDate, LocalDate endDate);

    List<Reserve> findByRoomAndStatus(Room room, ReservationStatus status);

    List<Reserve> findByGuestId(UUID guestId);

    @Query("SELECT r FROM Reserve r WHERE r.checkout < :date AND r.status = 'ACTIVE'")
    List<Reserve> findExpiredActiveReservations(@Param("date") LocalDate date);

    @Query("SELECT r FROM Reserve r WHERE r.checkout = :date AND r.status = 'ACTIVE'")
    List<Reserve> findReservationsEndingToday(@Param("date") LocalDate date);

    List<Reserve> findByRoomNumberAndDateRange(int roomNumber, LocalDate checkin, LocalDate checkout, ReservationStatus reservationStatus);

    List<Reserve> findByGuest(Guest guest);

    Long countByStatus(ReservationStatus status);
}
