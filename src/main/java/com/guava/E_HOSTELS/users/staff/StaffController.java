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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/staff")
public class StaffController {

    public static String uploadDirectory = System.getProperty("user.dir")+ "/src/main/webapp/images";

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

    @PostMapping("/update/{staffId}")
    public String updateStaffDetails(@PathVariable Long staffId,
                                        @ModelAttribute("staff") Staff updatedStaff,
                                        @RequestParam("image") MultipartFile file) throws IOException {

        String staffFolder = uploadDirectory + "/staff/"+ staffId;
        File directory = new File(staffFolder);
        //i first deleted the previous existing landlord's image folder
        if (directory.exists()) {
            deleteDirectory(directory);
        }
        // i then created a new folder
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate a unique numeric name for the file
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString().replaceAll("-", "") + fileExtension;
        Path fileNameAndPath = Paths.get(staffFolder, uniqueFileName);
        Files.write(fileNameAndPath, file.getBytes());

        updatedStaff.setPhoto(staffId+ "/"+ uniqueFileName);
        staffService.updateStaff(staffId, updatedStaff);
        return "redirect:/staff/" + staffId + "/home";
    }


    // Utility method to get file extension
    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf(".");
        if (lastIndexOfDot == -1) {
            return ""; // empty extension
        }
        return fileName.substring(lastIndexOfDot);
    }

    // Utility method to recursively delete a directory and its contents
    private void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }
}
