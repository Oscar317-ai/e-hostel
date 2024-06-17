package com.guava.E_HOSTELS.users.staff;

import com.guava.E_HOSTELS.users.director.Director;
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


    public void updateStaff(Long staffId, Staff updatedStaff) {
        Staff staffToUpdate = staffRepository.findById(staffId).orElse(null);
        if (staffToUpdate != null) {
            if (updatedStaff.getFirstName() != null) {
                staffToUpdate.setFirstName(updatedStaff.getFirstName());
            }
            if (updatedStaff.getLastName() != null) {
                staffToUpdate.setLastName(updatedStaff.getLastName());
            }
            if (updatedStaff.getPhoto() != null) {
                staffToUpdate.setPhoto(updatedStaff.getPhoto());
            }
            if (updatedStaff.getUserName() != null) {
                staffToUpdate.setUserName(updatedStaff.getUserName());
            }

            if (updatedStaff.getEmail() != null) {
                staffToUpdate.setEmail(updatedStaff.getEmail());
            }
            if (updatedStaff.getPassword() != null) {
                staffToUpdate.setPassword(updatedStaff.getPassword());
            }


            staffRepository.save(staffToUpdate);

        }
    }
}
