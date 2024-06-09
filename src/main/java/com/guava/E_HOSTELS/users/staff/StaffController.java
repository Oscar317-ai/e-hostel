package com.guava.E_HOSTELS.users.staff;

import com.guava.E_HOSTELS.hostel.building.Building;
import com.guava.E_HOSTELS.hostel.building.BuildingService;
import com.guava.E_HOSTELS.hostel.house.House;
import com.guava.E_HOSTELS.hostel.house.HouseService;
import com.guava.E_HOSTELS.users.director.Director;
import com.guava.E_HOSTELS.users.director.DirectorService;
import com.guava.E_HOSTELS.users.landlord.Landlord;
import com.guava.E_HOSTELS.users.landlord.LandlordService;
import com.guava.E_HOSTELS.users.tenant.Tenant;
import com.guava.E_HOSTELS.users.tenant.TenantService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final DirectorService directorService;
    private final StaffService staffService;
    private final TenantService tenantService;
    private final LandlordService landlordService;

    private final BuildingService buildingService;
    private final HouseService houseService;

    public StaffController(DirectorService directorService, StaffService staffService, TenantService tenantService, LandlordService landlordService, BuildingService buildingService, HouseService houseService) {
        this.directorService = directorService;
        this.staffService = staffService;
        this.tenantService = tenantService;
        this.landlordService = landlordService;
        this.buildingService = buildingService;
        this.houseService = houseService;
    }


    @GetMapping("/{id}/home")
    public String staffHome(@PathVariable Long id, Model model){
        Staff staff = staffService.findById(id);
        List<Tenant> tenants = tenantService.fetchAll();
        List<Landlord> landlords = landlordService.findAll();
        List<Staff> staffs = staffService.fetchAll();
        List<Building> buildings = buildingService.findAll();
        List<House> houses = houseService.findAll();


        model.addAttribute("tenants",tenants);
        model.addAttribute("landlords",landlords);
        model.addAttribute("staff",staff);
        model.addAttribute("staffs",staffs);
        model.addAttribute("buildings",buildings);
        model.addAttribute("houses",houses);
        return "/staff/staffhome";
    }
}
