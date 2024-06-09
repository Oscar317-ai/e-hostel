package com.guava.E_HOSTELS.users.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class TenantDetails implements UserDetails {

    private final Tenant tenant;

    @Autowired
    public TenantDetails(Tenant tenant) {
        this.tenant = tenant;
    }


    public Long getTenantId() {
        return tenant.getTenantId();
    }

    public String getUserName() {
        return tenant.getUserName();
    }

    public String getFirstName() {
        return tenant.getFirstName();
    }

    public String getLastName() {
        return tenant.getLastName();
    }

    public String getEmail() {
        return tenant.getEmail();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_TENANT"));
    }
    @Override
    public String getPassword() {
        return tenant.getPassword();
    }

    @Override
    public String getUsername() {
        return tenant.getUserName();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public Tenant getTenant() {
        return this.tenant;
    }
}
