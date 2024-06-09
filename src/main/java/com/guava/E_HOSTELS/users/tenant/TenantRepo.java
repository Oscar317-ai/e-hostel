package com.guava.E_HOSTELS.users.tenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepo extends JpaRepository<Tenant, Long> {
    Tenant findByUserName(String userName);

}
