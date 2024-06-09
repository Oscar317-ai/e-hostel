package com.guava.E_HOSTELS.users.director;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class DirectorDetails implements UserDetails {

    private final Director director;

    @Autowired
    public DirectorDetails(Director director) {
        this.director = director;
    }

    public Long getDirectorId() {
        return director.getDirectorId();
    }

    public Director getDirector(){ return this.director;}
    public String getUserName() {
        return director.getUserName();
    }

    public String getFirstName() {
        return director.getFirstName();
    }

    public String getLastName() {
        return director.getLastName();
    }

    public String getEmail() {
        return director.getEmail();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_DIRECTOR"));
    }

    @Override
    public String getPassword() {
        return director.getPassword();
    }

    @Override
    public String getUsername() {
        return director.getUserName();
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
