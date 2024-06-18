package com.guava.E_HOSTELS.users.landlord;

import com.guava.E_HOSTELS.users.director.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LandlordService {

    private final LandlordRepository landlordRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LandlordService(LandlordRepository landlordRepository, PasswordEncoder passwordEncoder) {
        this.landlordRepository = landlordRepository;
        this.passwordEncoder = passwordEncoder;
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


    public void updateLandlord(Long landlordId, Landlord updatedLandord) {
        Landlord landlordToUpdate = landlordRepository.findById(landlordId).orElse(null);
        if (landlordToUpdate != null) {
            if (updatedLandord.getFirstName() != null) {
                landlordToUpdate.setFirstName(updatedLandord.getFirstName());
            }
            if (updatedLandord.getLastName() != null) {
                landlordToUpdate.setLastName(updatedLandord.getLastName());
            }
            if (updatedLandord.getPhoto() != null) {
                landlordToUpdate.setPhoto(updatedLandord.getPhoto());
            }
            if (updatedLandord.getUserName() != null) {
                landlordToUpdate.setUserName(updatedLandord.getUserName());
            }

            if (updatedLandord.getEmail() != null) {
                landlordToUpdate.setEmail(updatedLandord.getEmail());
            }
            if (updatedLandord.getPassword() != null) {
                String password = passwordEncoder.encode(updatedLandord.getPassword());
                landlordToUpdate.setPassword(password);
            }

            landlordRepository.save(landlordToUpdate);

        }
    }
}
