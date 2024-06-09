package com.guava.E_HOSTELS.users.landlord;

import com.guava.E_HOSTELS.hostel.building.Building;
import com.guava.E_HOSTELS.hostel.building.BuildingService;
import com.guava.E_HOSTELS.hostel.house.House;
import com.guava.E_HOSTELS.hostel.house.HouseService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/landlord")
public class LandlordController {

    public static String uploadDirectory = System.getProperty("user.dir")+ "/src/main/webapp/images";

    @Autowired
    private LandlordService landlordService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private HouseService houseService;



    @GetMapping("/{landlordId}/home")
    public String getBuildings(@PathVariable Long landlordId, Model model) {
        Landlord landlord = landlordService.findById(landlordId);
        if (landlord != null) {
            model.addAttribute("landlord", landlord);
            model.addAttribute("buildings", landlord.getBuildings());
            model.addAttribute("building", new Building());
        }
        return "/landlord/landlordhome";
    }

    @PostMapping("/{landlordId}/buildings")
    public String createBuilding(@PathVariable Long landlordId,
                                 @ModelAttribute Building building,
                                 @RequestParam("image") MultipartFile file,
                                 Model model) throws IOException {
        Landlord landlord = landlordService.findById(landlordId);
        if (landlord != null) {
            building.setLandlord(landlord);

            // Handle file upload
            if (!file.isEmpty()) {
                // Create a subfolder named with the landlord's ID
                String landlordFolder = uploadDirectory +"/building/"+ "/" + landlordId;
                File directory = new File(landlordFolder);
                // Delete the previous existing landlord's image folder
                if (directory.exists()) {
                    deleteDirectory(directory);
                }
                // Create a new folder
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Generate a unique numeric name for the file
                String fileExtension = getFileExtension(file.getOriginalFilename());
                String uniqueFileName = UUID.randomUUID().toString().replaceAll("-", "") + fileExtension;
                Path fileNameAndPath = Paths.get(landlordFolder, uniqueFileName);
                Files.write(fileNameAndPath, file.getBytes());

                // Update building record with the relative path of the image
                building.setPhoto(landlordId + "/" + uniqueFileName);
            }

            // Save the building object
            building = buildingService.save(building);
            model.addAttribute("building", building);

            // Create houses for the building
            for (int i = 1; i <= building.getTotalHouses(); i++) {
                House house = new House();
                house.setDoor_no(i);
                house.setBuilding(building);
                //house.setOccupied(false);
                houseService.save(house);
            }
        }

        return "redirect:/landlord/" + landlordId + "/home";
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

