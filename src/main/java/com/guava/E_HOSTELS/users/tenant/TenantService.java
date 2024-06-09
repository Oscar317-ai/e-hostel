package com.guava.E_HOSTELS.users.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    public Tenant findById(Long tenantId) {
       return tenantRepository.findByTenantId(tenantId);
    }

    public Tenant findByUsername(String userName){
        return tenantRepository.findByUserName(userName);
    }

    public Tenant saveTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public Tenant findByemail(String email) {
        return tenantRepository.findByemail(email);
    }

    public List<Tenant> fetchAll() {
        return  tenantRepository.findAll();
    }
}
