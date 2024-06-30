package com.guava.E_HOSTELS.users.director;

import com.guava.E_HOSTELS.hostel.building.Building;
import com.guava.E_HOSTELS.hostel.building.BuildingService;
import com.guava.E_HOSTELS.hostel.house.House;
import com.guava.E_HOSTELS.hostel.house.HouseService;
import com.guava.E_HOSTELS.users.landlord.Landlord;
import com.guava.E_HOSTELS.users.landlord.LandlordService;
import com.guava.E_HOSTELS.users.staff.Staff;
import com.guava.E_HOSTELS.users.staff.StaffService;
import com.guava.E_HOSTELS.users.tenant.Tenant;
import com.guava.E_HOSTELS.users.tenant.TenantService;
import lombok.RequiredArgsConstructor;
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

// DirectorController.java
@Controller
@RequiredArgsConstructor
@RequestMapping("/director")
public class DirectorController {

    public static String uploadDirectory = System.getProperty("user.dir")+ "/src/main/webapp/images";

    private final DirectorService directorService;
    private final StaffService staffService;
    private final TenantService tenantService;
    private final LandlordService landlordService;

    private final BuildingService buildingService;
    private final HouseService houseService;


    @GetMapping("/{id}/home")
    public String directorHome(@PathVariable Long id, Model model) {
        Director director = directorService.getDirectorById(id);
       List<Tenant> tenants = tenantService.fetchAll();
        List<Landlord> landlords = landlordService.findAll();
        List<Staff> staff = staffService.fetchAll();
        List<Building> buildings = buildingService.findAll();
        List<House> houses = houseService.findAll();

        model.addAttribute("director",director);
        model.addAttribute("tenants",tenants);
        model.addAttribute("landlords",landlords);
        model.addAttribute("staff",staff);
        model.addAttribute("buildings",buildings);
        model.addAttribute("houses",houses);

        return "/director/directorhome";
    }


    @PostMapping("/update/{directorId}")
    public String updateDirectorDetails(@PathVariable Long directorId,
                                     @ModelAttribute("director") Director updatedDirector,
                                     @RequestParam("image") MultipartFile file) throws IOException {

        String houseFolder = uploadDirectory + "/director/"+ directorId;
        File directory = new File(houseFolder);
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
        Path fileNameAndPath = Paths.get(houseFolder, uniqueFileName);
        Files.write(fileNameAndPath, file.getBytes());

        updatedDirector.setPhoto(directorId+ "/"+ uniqueFileName);
        directorService.updateDirector(directorId, updatedDirector);
        return "redirect:/director/" + directorId + "/home";
    }

    // Add more endpoints for CRUD operations as needed




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

