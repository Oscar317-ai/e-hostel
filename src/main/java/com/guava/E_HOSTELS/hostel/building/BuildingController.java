package com.guava.E_HOSTELS.hostel.building;


import com.guava.E_HOSTELS.hostel.house.House;
import com.guava.E_HOSTELS.hostel.house.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/building")
public class BuildingController {

    private final HouseService houseService;
    private final BuildingService buildingService;

    public BuildingController(HouseService houseService, BuildingService buildingService) {
        this.houseService = houseService;
        this.buildingService = buildingService;
    }

    // landlords view of the building
    @GetMapping("/landlords-building-view/{buildingId}")
    public String landlordGetBuilding(@PathVariable Long buildingId, Model model) {
        Building building = buildingService.findById(buildingId);

        System.out.println("vacanthouses " +building.countVacantHouses());
        if (building != null) {
            House house = new House();
            House home = new House();
            model.addAttribute("building", building);
            model.addAttribute("home", home);
            model.addAttribute("houses", building.getHouses());
        }
        return "/building/landlords-view";
    }
    // landlords view of the house
    @GetMapping("/landlords-house-view/{houseId}")
    public String vacantHouse(@PathVariable Long houseId, Model model) {
        House house = houseService.findById(houseId);
        if (house != null) {
            model.addAttribute("house", house);
        }
        return "/house/landlords-view";
    }

    // tenants view of the building
    @GetMapping("/tenants-building-view/{buildingId}")
    public String tenantGetBuilding(@PathVariable Long buildingId, Model model) {
        Building building = buildingService.findById(buildingId);

        if (building != null) {
            House house = new House();
            House home = new House();
            model.addAttribute("building", building);
            model.addAttribute("home", home);
            model.addAttribute("houses", building.getHouses());
        }
        return "/building/tenants-view";
    }

    // tenants view of the house
    @GetMapping("/tenants-house-view/{houseId}")
    public String vacantHouseTenantView(@PathVariable Long houseId, Model model) {
        House house = houseService.findById(houseId);
        if (house != null) {
            model.addAttribute("house", house);
        }
        return "/house/vacant-house";
    }


    // Other methods for building-related operations can be added here
}
