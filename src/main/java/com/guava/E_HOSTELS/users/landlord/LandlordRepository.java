package com.guava.E_HOSTELS.users.landlord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandlordRepository extends JpaRepository<Landlord,Long> {
    Landlord findByemail(String email);
    Landlord findByUserName(String userName);
}
