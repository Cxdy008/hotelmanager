package com.hotelmanager.repositories;

import com.hotelmanager.models.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReserveRepository extends JpaRepository<Reserve, UUID> {
}
