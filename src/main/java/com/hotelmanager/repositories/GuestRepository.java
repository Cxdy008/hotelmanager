package com.hotelmanager.repositories;

import com.hotelmanager.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
    Optional<Guest> findByEmail(String email);

    boolean existsByDocument(String document);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Guest g JOIN g.reserves r WHERE g.id = :id AND r.status = 'ACTIVE'")
    boolean hasActiveReservations(@Param("id") Integer id);
}
