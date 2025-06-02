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

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
    List<Reserve> findByStatus(ReservationStatus status);

    List<Reserve> findByCheckinBetween(LocalDate startDate, LocalDate endDate);

    List<Reserve> findByCheckoutBetween(LocalDate startDate, LocalDate endDate);

    List<Reserve> findByRoomAndStatus(Room room, ReservationStatus status);

    List<Reserve> findByGuestId(Integer guestId);

    @Query("SELECT r FROM Reserve r WHERE r.checkout < :date AND r.status = 'ACTIVE'")
    List<Reserve> findExpiredActiveReservations(@Param("date") LocalDate date);

    @Query("SELECT r FROM Reserve r WHERE r.checkout = :date AND r.status = 'ACTIVE'")
    List<Reserve> findReservationsEndingToday(@Param("date") LocalDate date);

    @Query("SELECT r FROM Reserve r JOIN r.room rm WHERE rm.number = :roomNumber AND r.checkin <= :checkout AND r.checkout >= :checkin AND r.status = :status")
    List<Reserve> findByRoomNumberAndDateRange(
            @Param("roomNumber") int roomNumber,
            @Param("checkin") LocalDate checkin,
            @Param("checkout") LocalDate checkout,
            @Param("status") ReservationStatus status);

    List<Reserve> findByGuest(Guest guest);

    Long countByStatus(ReservationStatus status);
}
