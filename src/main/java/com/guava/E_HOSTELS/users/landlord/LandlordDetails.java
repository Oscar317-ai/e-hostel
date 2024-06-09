package com.guava.E_HOSTELS.users.landlord;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class LandlordDetails implements UserDetails {

    private final Landlord landlord;

    @Autowired
    public LandlordDetails(Landlord landlord) {
        this.landlord = landlord;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_LANDLORD"));
    }

    @Override
    public String getPassword() {
        return landlord.getPassword();
    }

    @Override
    public String getUsername() {
        return landlord.getUserName();
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
