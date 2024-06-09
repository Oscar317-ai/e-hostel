package com.guava.E_HOSTELS.security;

import com.guava.E_HOSTELS.users.director.DirectorDetails;
import com.guava.E_HOSTELS.users.landlord.LandlordDetails;
import com.guava.E_HOSTELS.users.staff.StaffDetails;
import com.guava.E_HOSTELS.users.tenant.TenantDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Getter
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private String userEmail;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Check the type of user and redirect accordingly
        Object principal = authentication.getPrincipal();


        String redirectUrl = "";
        if (principal instanceof TenantDetails) {
            TenantDetails tenantDetails = (TenantDetails) principal;
            redirectUrl = "/tenant/" + tenantDetails.getTenant().getTenantId() + "/home";
            userEmail = tenantDetails.getEmail();
        } else if (principal instanceof LandlordDetails) {
            LandlordDetails landlordDetails = (LandlordDetails) principal;
            redirectUrl = "/landlord/" + landlordDetails.getLandlord().getLandlordId() + "/home";
            userEmail = landlordDetails.getLandlord().getEmail();
        }
        else if (principal instanceof StaffDetails){
            StaffDetails staffDetails = (StaffDetails) principal;
            redirectUrl = "/staff/" + staffDetails.getStaff().getStaffId() + "/home";
            userEmail = staffDetails.getEmail();
        }
        else if (principal instanceof DirectorDetails){
            DirectorDetails directorDetails = (DirectorDetails) principal;
             redirectUrl = "/director/" + directorDetails.getDirector().getDirectorId()+ "/home";
             userEmail = directorDetails.getEmail();
        }

        response.sendRedirect(redirectUrl);
    }


}
