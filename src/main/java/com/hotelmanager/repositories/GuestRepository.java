package com.hotelmanager.repositories;

import com.hotelmanager.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
    Optional<Guest> findByEmail(String email);

    boolean existsByDocument(String document);

    Map<Object, Object> findByUsername(String admin);

    boolean hasActiveReservations(Integer id);
}
