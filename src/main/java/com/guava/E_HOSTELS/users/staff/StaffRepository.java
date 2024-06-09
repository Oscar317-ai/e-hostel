package com.guava.E_HOSTELS.users.staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {
    Staff findByUserName(String username);

    Staff findByemail(String email);
}
