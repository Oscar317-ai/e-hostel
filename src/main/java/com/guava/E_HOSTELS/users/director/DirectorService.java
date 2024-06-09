package com.guava.E_HOSTELS.users.director;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

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
}
