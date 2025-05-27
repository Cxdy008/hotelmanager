package com.hotelmanager.services;

import com.hotelmanager.enums.GuestRole;
import com.hotelmanager.models.Guest;
import com.hotelmanager.repositories.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private GuestRepository guestRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if(guestRepository.findByUsername("Admin").isEmpty()) {
            Guest guest = new Guest();
            guest.setName("Admin");
            guest.setEmail("admin@mail.com");
            guest.setPhone("000000000");
            guest.setDocument("hotel-admin");
            guest.setPassword(passwordEncoder.encode("admin@1234"));
            guest.setRole(GuestRole.ADMIN);
            guestRepository.save(guest);
            System.out.println("Admin has been created");
        } else {
            System.out.println("Admin already exists");
        }
    }
}
