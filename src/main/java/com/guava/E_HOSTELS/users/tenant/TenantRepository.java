package com.guava.E_HOSTELS.users.tenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Tenant findByTenantId(Long tenantId);
    Tenant findByUserName(String userName);
    Tenant findByemail(String email);
}

