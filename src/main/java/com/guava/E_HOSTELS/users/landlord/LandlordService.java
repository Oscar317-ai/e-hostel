package com.guava.E_HOSTELS.users.landlord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LandlordService {

    private final LandlordRepository landlordRepository;

    @Autowired
    public LandlordService(LandlordRepository landlordRepository) {
        this.landlordRepository = landlordRepository;
    }

    // Method to retrieve current logged-in landlord details
    public Landlord getCurrentLandlord() {
        // Logic to get current logged-in landlord from session or security context
        // For simplicity, let's assume we have a method to get current user's username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Retrieve landlord details from the database using repository
        return landlordRepository.findByUserName(username);
    }

    public Landlord findByUsername(String username) {
        return landlordRepository.findByUserName(username);
    }

    public Landlord saveLandlord(Landlord landlord) {
        return landlordRepository.save(landlord);
    }

    public Landlord save(Landlord landlord) {
        return landlordRepository.save(landlord);
    }

    public Landlord findById(Long id) {
        return landlordRepository.findById(id).orElse(null);
    }

    public List<Landlord> findAll() {
        return landlordRepository.findAll();
    }

    public Landlord findByemail(String email) {
        return landlordRepository.findByemail(email);
    }
}
