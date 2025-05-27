package com.hotelmanager.services;

import com.hotelmanager.repositories.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReserveService {
    @Autowired
    ReserveRepository repository;



}
