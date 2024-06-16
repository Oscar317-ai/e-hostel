package com.guava.E_HOSTELS.hostel.building;


import com.guava.E_HOSTELS.hostel.house.House;
import com.guava.E_HOSTELS.hostel.house.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/building")
public class BuildingController {

    public static String uploadDirectory = System.getProperty("user.dir")+ "/src/main/webapp/images";

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

    // Edit building
    @GetMapping("/edit/{buildingId}")
    public String editBuilding(@PathVariable Long buildingId, Model model) {
        Building building = buildingService.findById(buildingId);
        if (building != null) {
            model.addAttribute("building", building);
        }
        return "/building/edit";
    }

    @PostMapping("/update/{buildingId}")
    public String updateBuilding(@ModelAttribute("building") Building building,
                                 @PathVariable Long buildingId,
                                 @RequestParam("image") MultipartFile file) throws IOException {

        String buildingFolder = uploadDirectory + "/building/"+ buildingId;
        File directory = new File(buildingFolder);
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
        Path fileNameAndPath = Paths.get(buildingFolder, uniqueFileName);
        Files.write(fileNameAndPath, file.getBytes());

        building.setPhoto(buildingId + "/" + uniqueFileName);


        buildingService.updateBuilding(buildingId,building);
        return "redirect:/building/landlords-building-view/" + building.getBuildingId();
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

    // Other methods for building-related operations can be added here
}
