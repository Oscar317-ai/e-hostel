package com.guava.E_HOSTELS.utilities;

import com.guava.E_HOSTELS.users.director.Director;
import com.guava.E_HOSTELS.users.landlord.Landlord;
import com.guava.E_HOSTELS.users.staff.Staff;
import com.guava.E_HOSTELS.users.tenant.Tenant;
import com.guava.E_HOSTELS.users.director.DirectorService;
import com.guava.E_HOSTELS.users.landlord.LandlordService;
import com.guava.E_HOSTELS.users.staff.StaffService;
import com.guava.E_HOSTELS.users.tenant.TenantService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class MainController {


    public static String uploadDirectory = System.getProperty("user.dir")+ "/src/main/webapp/images";

    private final PasswordEncoder passwordEncoder;
    private final TenantService tenantService;
    private final LandlordService landlordService;
    private final StaffService staffService;
    private final DirectorService directorService;

    public MainController(PasswordEncoder passwordEncoder, StaffService staffService, TenantService tenantService, LandlordService landlordService,DirectorService directorService) {
        this.passwordEncoder = passwordEncoder;
        this.tenantService = tenantService;
        this.landlordService = landlordService;
        this.staffService = staffService;

        this.directorService = directorService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register/landlord")
    public String registerLandlord() {
        return "/landlord/landlordRegister";
    }

    @GetMapping("/register/tenant")
    public String registerTenant() {
        return "/tenant/tenantRegister";
    }

    @GetMapping("/register/staff")
    public String registerStaff() {
        return "/staff/staffRegister";
    }

    @GetMapping("/register/director")
    public String registerDirector() {
        return "/director/directorRegister";
    }

    @GetMapping("/payment-failed-error")
    public String paymentErrorPage() {
        return "/error-pages/payment-failed";
    }

    @GetMapping("/page-not-found")
    public String PageNotFound() {
        return "/error-pages/page-not-found";
    }



    @PostMapping("/save/tenant")
    public String saveStudent(@ModelAttribute Tenant tenant,
                              @RequestParam("image") MultipartFile file, Model model) throws IOException {
        // Save tenant data first to get the generated ID
        String hashed_password = passwordEncoder.encode(tenant.getPassword());

        tenant.setPassword(hashed_password);
        Tenant savedTenant = tenantService.saveTenant(tenant);

        Long tenantId = savedTenant.getTenantId();

        // Create a subfolder named with the tenant's ID
        String tenantFolder = uploadDirectory + "/tenant/" + tenantId;
        File directory = new File(tenantFolder);
        //i first deleted the previous existing tenant's image folder
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
        Path fileNameAndPath = Paths.get(tenantFolder, uniqueFileName);
        Files.write(fileNameAndPath, file.getBytes());

        // Update tenant record with the relative path of the profile image
        savedTenant.setPhoto(tenantId + "/" + uniqueFileName);
        tenantService.saveTenant(savedTenant);


        return "redirect:/login";
    }


    @PostMapping("/save/landlord")
    public String saveLandlord(@ModelAttribute Landlord landlord,
                               @RequestParam("image") MultipartFile file, Model model) throws IOException {
        // Save landlord data first to get the generated ID
        String hashed_password = passwordEncoder.encode(landlord.getPassword());
        ;
        landlord.setPassword(hashed_password);
        Landlord savedLandlord = landlordService.saveLandlord(landlord);

        Long landlordId = savedLandlord.getLandlordId();

        // Create a subfolder named with the landlord's ID
        String landlordFolder = uploadDirectory + "/landlord/" + landlordId;
        File directory = new File(landlordFolder);
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
        Path fileNameAndPath = Paths.get(landlordFolder, uniqueFileName);
        Files.write(fileNameAndPath, file.getBytes());

        // Update landlord record with the relative path of the profile image
        savedLandlord.setPhoto(landlordId + "/" + uniqueFileName);
        landlordService.saveLandlord(savedLandlord);


        return "redirect:/login";
    }


    @PostMapping("/save/staff")
    public String saveStudent(@ModelAttribute Staff staff,
                              @RequestParam("image") MultipartFile file, Model model) throws IOException {
        // Save staff data first to get the generated ID
        String hashed_password = passwordEncoder.encode(staff.getPassword());

        staff.setPassword(hashed_password);
        Staff savedStaff = staffService.saveStaff(staff);

        Long staffId = savedStaff.getStaffId();

        // Create a subfolder named with the staff's ID
        String staffFolder = uploadDirectory + "/staff/" + staffId;
        File directory = new File(staffFolder);
        //i first deleted the previous existing staff's image folder
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

        // Update staff record with the relative path of the profile image
        savedStaff.setPhoto(staffId + "/" + uniqueFileName);
        staffService.saveStaff(savedStaff);


        return "redirect:/login";
    }




    @PostMapping("/save/director")
    public String saveDirector(@ModelAttribute Director director,
                              @RequestParam("image") MultipartFile file, Model model) throws IOException {
        // Save director data first to get the generated ID
        String hashed_password = passwordEncoder.encode(director.getPassword());

        director.setPassword(hashed_password);
        Director savedDirector = directorService.saveDirector(director);

        Long directorId = savedDirector.getDirectorId();

        // Create a subfolder named with the director's ID
        String directorFolder = uploadDirectory + "/director/" + directorId;
        File directory = new File(directorFolder);
        //i first deleted the previous existing director's image folder
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
        Path fileNameAndPath = Paths.get(directorFolder, uniqueFileName);
        Files.write(fileNameAndPath, file.getBytes());

        // Update director record with the relative path of the profile image
        savedDirector.setPhoto(directorId + "/" + uniqueFileName);
        directorService.saveDirector(savedDirector);


        return "redirect:/login";
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
