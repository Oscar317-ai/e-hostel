package com.guava.E_HOSTELS.users.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class StaffDetails implements UserDetails {

    private final Staff staff;

    @Autowired
    public StaffDetails(Staff staff) {
        this.staff = staff;
    }

    public Long getStaffId() {
        return staff.getStaffId();
    }

    public Staff getStaff(){ return this.staff;}
    public String getUserName() {
        return staff.getUserName();
    }

    public String getFirstName() {
        return staff.getFirstName();
    }

    public String getLastName() {
        return staff.getLastName();
    }

    public String getEmail() {
        return staff.getEmail();
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_STAFF"));
    }

    @Override
    public String getPassword() {
        return staff.getPassword();
    }

    @Override
    public String getUsername() {
        return staff.getUserName();
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
}
