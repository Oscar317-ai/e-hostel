package com.guava.E_HOSTELS.users.director;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director,Long> {
    Director findByUserName(String username);

    Director findByemail(String email);
}
