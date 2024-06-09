package com.guava.E_HOSTELS.users.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;
    public Staff findByUsername(String username) {
        return staffRepository.findByUserName(username);
    }

    public Staff getCurrentStaff() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return staffRepository.findByUserName(username);
    }

    public Staff findById(Long id){
        return staffRepository.findById(id).orElse(null);
    }

    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public Staff findByemail(String email) {
        return staffRepository.findByemail(email);
    }

    public List<Staff> fetchAll() {
        return staffRepository.findAll();
    }
}
