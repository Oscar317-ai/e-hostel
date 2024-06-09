package com.guava.E_HOSTELS.hostel.house;

import com.guava.E_HOSTELS.hostel.building.Building;
import com.guava.E_HOSTELS.hostel.building.BuildingService;
import com.guava.E_HOSTELS.payments.PaymentService;
import com.guava.E_HOSTELS.security.CustomAuthenticationSuccessHandler;
import com.guava.E_HOSTELS.users.tenant.Tenant;
import com.guava.E_HOSTELS.users.tenant.TenantRepo;
import com.guava.E_HOSTELS.users.tenant.TenantService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController {

    private final CustomAuthenticationSuccessHandler successHandler;
    private final PaymentService paymentService;
    private final HouseRepository houseRepository;
    private final BuildingService buildingService;
    private final HouseService houseService;
    private final TenantRepo tenantRepo;
    private final TenantService tenantService;

    public HouseController(CustomAuthenticationSuccessHandler successHandler, PaymentService paymentService, HouseRepository houseRepository, BuildingService buildingService, HouseService houseService, TenantRepo tenantRepo, TenantService tenantService) {
        this.successHandler = successHandler;
        this.paymentService = paymentService;
        this.houseRepository = houseRepository;
        this.buildingService = buildingService;
        this.houseService = houseService;
        this.tenantRepo = tenantRepo;
        this.tenantService = tenantService;
    }

    @GetMapping("/tenant-house/{houseId}")
    public String getHouseDetails(@PathVariable Long houseId, Model model) {
        House house = houseService.findById(houseId);
        if (house != null) {
            model.addAttribute("house", house);
        }
        return "/tenant/tenants-house";
    }

    @GetMapping("/vacant/{houseId}")
    public String vacantHouse(@PathVariable Long houseId, Model model) {
        House house = houseService.findById(houseId);
        if (house != null) {
            model.addAttribute("house", house);
        }
        return "/house/vacant-house";
    }


//    @PostMapping("/update-bookings/{houseId}")
//    public ResponseEntity<Map<String, Boolean>> updateBookings(@PathVariable Long houseId, @RequestBody Map<String, Integer> request) {
//        House house = houseService.findById(houseId);
//        if (house != null) {
//            int increment = request.get("increment");
//            System.out.println("total bookings: "+ house.getTotalBookings());
//            house.setTotalBookings(house.getTotalBookings() + increment);
//            System.out.println("total bookings: "+ house.getTotalBookings());
//            houseService.save(house);
//            return ResponseEntity.ok(Collections.singletonMap("success", true));
//        }
//        return ResponseEntity.ok(Collections.singletonMap("success", false));
//    }

    @PostMapping("/update-bookings/{houseId}")
    public ResponseEntity<Map<String, Boolean>> updateBookings(@PathVariable Long houseId, @RequestBody Map<String, Integer> request) {
        House house = houseService.findById(houseId);
        if (house != null) {
            int increment = request.get("increment");
            house.updateBookings(increment);
            houseService.save(house); // Persist the house changes

            Building building = house.getBuilding();
            if (building != null) {
                building.updateTotalDemand();
                buildingService.save(building); // Persist the building changes
            }

            return ResponseEntity.ok(Collections.singletonMap("success", true));
        }
        return ResponseEntity.ok(Collections.singletonMap("success", false));
    }




    @PostMapping("/rent/{houseId}")
    public ResponseEntity<Map<String, Boolean>> rentHouse(@PathVariable Long houseId, @RequestBody Map<String, String> request) {
        // Get the current tenant's email

       String userEmail = successHandler.getUserEmail();

        Tenant tenant = tenantService.findByemail(userEmail);

        if (tenant != null) {
            int idNumber = Integer.parseInt(request.get("idNumber"));
            String phoneNumber = request.get("phoneNumber");

            System.out.println("id: "+idNumber+" phone no: "+ phoneNumber);
            tenant.setIdNumber(idNumber);


            ResponseEntity<String> paymentResponse = paymentService.processPayment(phoneNumber);

            if (!paymentResponse.getBody().equals("success")) {
                // Handle payment failure (e.g., return error message)
                System.out.println("payment failed early ");
                return ResponseEntity.badRequest().body(Collections.singletonMap("success", false));
            }



            House house = houseService.findById(houseId);
            if (house != null && house.getHouseStatus() == HouseStatus.VACANT) {
                List<House> tenantHouses  = tenant.getHouses();
                tenantHouses.add(house);
                tenantService.saveTenant(tenant);
                house.setTenant(tenant);
                house.setHouseStatus(HouseStatus.OCCUPIED);
                houseService.save(house);
                return ResponseEntity.ok(Collections.singletonMap("success", true));
            }


        }
        return ResponseEntity.ok(Collections.singletonMap("success", false));
    }


    @PostMapping("/update/{houseId}")
    public String updateHouseDetails(@PathVariable Long houseId, @ModelAttribute("house") House updatedHouse) {
        houseService.updateHouse(houseId, updatedHouse);
        return "redirect:/house/details/" + houseId;
    }

    @GetMapping("/delete/{houseId}")
    public String deleteHouse(@PathVariable Long houseId) {
        House house = houseService.findById(houseId);
        if (house != null) {
            Long buildingId = house.getBuilding().getBuildingId(); // Assuming House has a getBuilding() method
            houseService.deleteHouse(houseId);
            return "redirect:/building/details/" + buildingId;
        }
        return "redirect:/error"; // Redirect to an error page or handle the error appropriately
    }
}
