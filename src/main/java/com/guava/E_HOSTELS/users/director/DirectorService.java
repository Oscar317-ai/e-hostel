package com.guava.E_HOSTELS.users.director;

import com.guava.E_HOSTELS.hostel.house.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DirectorService {

    @Autowired
    private final DirectorRepository directorRepository;
    private final PasswordEncoder passwordEncoder;

    public DirectorService(DirectorRepository directorRepository, PasswordEncoder passwordEncoder) {
        this.directorRepository = directorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Director findByUserName(String username) {
        return directorRepository.findByUserName(username);
    }

    public Director getCurrentDirector() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return directorRepository.findByUserName(username);
    }

    public Director saveDirector(Director director) {
       return directorRepository.save(director);
    }

    public Director findByemail(String email) {
        return directorRepository.findByemail(email);
    }

    public Director getDirectorById(Long id) {
        return directorRepository.findById(id).orElse(null);
    }

    public void updateDirector(Long directorId, Director updatedDirector) {

        Director directorToUpdate = directorRepository.findById(directorId).orElse(null);
        if (directorToUpdate != null) {
            if (updatedDirector.getFirstName() != null) {
                directorToUpdate.setFirstName(updatedDirector.getFirstName());
            }
            if (updatedDirector.getLastName() != null) {
                directorToUpdate.setLastName(updatedDirector.getLastName());
            }
            if (updatedDirector.getPhoto() != null) {
                directorToUpdate.setPhoto(updatedDirector.getPhoto());
            }
            if (updatedDirector.getUserName() != null) {
                directorToUpdate.setUserName(updatedDirector.getUserName());
            }

            if (updatedDirector.getEmail() != null) {
                directorToUpdate.setEmail(updatedDirector.getEmail());
            }
            if (updatedDirector.getPassword() != null) {
                String password = passwordEncoder.encode(updatedDirector.getPassword());
                directorToUpdate.setPassword(password);
            }


            directorRepository.save(directorToUpdate);

        }
    }
}
