package com.travelbnb.repository;

import com.travelbnb.entity.AppUser;
import com.travelbnb.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingsRepository extends JpaRepository<Bookings, Long> {
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}