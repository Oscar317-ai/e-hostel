package com.guava.E_HOSTELS.users.tenant;


import com.guava.E_HOSTELS.hostel.building.Building;
import com.guava.E_HOSTELS.hostel.building.BuildingRepository;
import com.guava.E_HOSTELS.hostel.building.BuildingService;
import com.guava.E_HOSTELS.hostel.house.House;
import com.guava.E_HOSTELS.hostel.house.HouseService;
import com.guava.E_HOSTELS.hostel.house.HouseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    private TenantService tenantService;
    @Autowired
    private HouseService houseService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private BuildingRepository buildingRepository;

    @GetMapping("/{tenantId}/home")
    public String getMyHomes(@PathVariable Long tenantId, Model model,
                             @RequestParam(required = false) String keyword,
                             @RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(defaultValue = "buildingName") String sortField,
                             @RequestParam(defaultValue = "asc") String sortOrder) {

        Tenant tenant = tenantService.findById(tenantId);
        List<House> myHomes = houseService.findHousesByTenantId(tenantId);

        int pageSize = 8;
        Sort sort = Sort.by(sortField);
        sort = sortOrder.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<Building> buildings = (keyword != null && !keyword.isEmpty())
                ? buildingService.searchByNameOrArea(keyword, keyword, pageable)
                : buildingRepository.findAllWithLandlord(pageable);

        model.addAttribute("buildings", buildings.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", buildings.getTotalPages());
        model.addAttribute("tenant", tenant);
        model.addAttribute("myHomes", myHomes);
        model.addAttribute("tenantId", tenantId);
        return "tenant/tenanthome";
    }

    @GetMapping("/{tenantId}/availablehomes")
    public String getAvailableHomes(@PathVariable Long tenantId, Model model) {
        List<House> houses = houseService.findVacantHouses();
        model.addAttribute("houses", houses);
        model.addAttribute("tenantId", tenantId);
        return "availablehomes";
    }

    @PostMapping("/{tenantId}/bookhouse/{houseId}")
    public String bookHouse(@PathVariable Long tenantId, @PathVariable Long houseId, RedirectAttributes redirectAttributes) {
        House house = houseService.findById(houseId);
        Tenant tenant = tenantService.findById(tenantId);

        if (house != null && tenant != null) {
            if (house.getHouseStatus() == HouseStatus.VACANT) {
                house.setTenant(tenant);
                house.setHouseStatus(HouseStatus.OCCUPIED);
                houseService.save(house);
                redirectAttributes.addFlashAttribute("message", "House successfully rented.");
            } else if (house.getHouseStatus() == HouseStatus.OCCUPIED) {
                // Logic for booking a house
                // Notify the tenant when the house becomes vacant
                // You might need to implement a notification system here
                redirectAttributes.addFlashAttribute("message", "House is currently occupied. You will be notified when it becomes vacant.");
            } else if (house.getHouseStatus() == HouseStatus.UNAVAILABLE) {
                redirectAttributes.addFlashAttribute("message", "House is unavailable.");
            }
        }

        return "redirect:/tenant/" + tenantId + "/home";
    }
}