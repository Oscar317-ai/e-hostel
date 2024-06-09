package com.guava.E_HOSTELS.security;

import  com.guava.E_HOSTELS.users.landlord.LandlordDetails;
import com.guava.E_HOSTELS.users.director.Director;
import com.guava.E_HOSTELS.users.director.DirectorDetails;
import com.guava.E_HOSTELS.users.director.DirectorService;
import com.guava.E_HOSTELS.users.landlord.Landlord;
import com.guava.E_HOSTELS.users.landlord.LandlordService;
import com.guava.E_HOSTELS.users.staff.Staff;
import com.guava.E_HOSTELS.users.staff.StaffDetails;
import com.guava.E_HOSTELS.users.staff.StaffService;
import com.guava.E_HOSTELS.users.tenant.Tenant;
import com.guava.E_HOSTELS.users.tenant.TenantDetails;
import com.guava.E_HOSTELS.users.tenant.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class CustomUserDetailsService implements UserDetailsService {

    private final TenantService tenantService;

    private final LandlordService landlordService;

    private final StaffService staffService;

    private final DirectorService directorService;

    @Autowired
    public CustomUserDetailsService(TenantService tenantService, LandlordService landlordService, StaffService staffService, DirectorService directorService) {
        this.tenantService = tenantService;
        this.landlordService = landlordService;
        this.staffService = staffService;
        this.directorService = directorService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Landlord landlord = landlordService.findByemail(username);
        Tenant tenant = tenantService.findByemail(username);
        Staff staff = staffService.findByemail(username);
        Director director = directorService.findByemail(username);


        if (tenant != null){
            return new TenantDetails(tenant);
        }
        else if (landlord != null){
            return new LandlordDetails(landlord);
        }
        else if (staff != null){
            return new StaffDetails(staff);
        }
        else if (director != null){
            return new DirectorDetails(director);
        }
        else
            throw new UsernameNotFoundException("Unable to load user");

    }
}
