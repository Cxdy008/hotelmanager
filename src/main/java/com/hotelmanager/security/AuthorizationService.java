package com.hotelmanager.security;

import com.hotelmanager.models.Guest;
import com.hotelmanager.repositories.GuestRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationService implements UserDetailsService {
    @Autowired
    GuestRepository guestRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Guest guest = guestRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(guest.getEmail(), guest.getPassword(), guest.getAuthorities());
    }
}
