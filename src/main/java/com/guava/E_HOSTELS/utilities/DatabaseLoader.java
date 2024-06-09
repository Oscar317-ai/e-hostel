package com.guava.E_HOSTELS.utilities;

import com.guava.E_HOSTELS.hostel.building.Building;
import com.guava.E_HOSTELS.hostel.building.BuildingRepository;
import com.guava.E_HOSTELS.hostel.building.BuildingService;
import com.guava.E_HOSTELS.hostel.house.House;
import com.guava.E_HOSTELS.hostel.house.HouseService;
import com.guava.E_HOSTELS.hostel.house.HouseStatus;
import com.guava.E_HOSTELS.users.director.Director;
import com.guava.E_HOSTELS.users.director.DirectorService;
import com.guava.E_HOSTELS.users.landlord.Landlord;
import com.guava.E_HOSTELS.users.landlord.LandlordService;
import com.guava.E_HOSTELS.users.staff.Staff;
import com.guava.E_HOSTELS.users.staff.StaffService;
import com.guava.E_HOSTELS.users.tenant.Tenant;
import com.guava.E_HOSTELS.users.tenant.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseLoader {



    private final BuildingRepository buildingRepository;
    private final DirectorService directorService;
    private final StaffService staffService;
    private final LandlordService landlordService;
    private final TenantService tenantService;
    private final BuildingService buildingService;
    private final HouseService houseService;
    private final PasswordEncoder passwordEncoder;

    public DatabaseLoader(BuildingRepository buildingRepository, DirectorService directorService, StaffService staffService, LandlordService landlordService, TenantService tenantService, BuildingService buildingService, HouseService houseService, PasswordEncoder passwordEncoder) {
        this.buildingRepository = buildingRepository;
        this.directorService = directorService;
        this.staffService = staffService;
        this.landlordService = landlordService;
        this.tenantService = tenantService;
        this.buildingService = buildingService;
        this.houseService = houseService;
        this.passwordEncoder = passwordEncoder;
    }


            @Bean
            public CommandLineRunner databaseInit() {
                return args -> {

                    String password = "1234";
                    String encoded = passwordEncoder.encode(password);
                    // Create landlord
                    Landlord landlord = new Landlord("john", "john@gmail.com", encoded);
                    landlordService.save(landlord);

                    Director director = new Director("Oscar317-ai","oscar@gmail.com", encoded);
                    directorService.saveDirector(director);

                    Staff staff = new Staff("jay","jay@gmail.com",encoded);
                    staffService.saveStaff(staff);

                    // Create tenant
                    Tenant tenant = new Tenant("joy", "joy@gmail.com", encoded);
                    Tenant tenant1 = new Tenant("Alice", "ali@gmail.com", encoded);
                    Tenant tenant2 = new Tenant("domi", "domi@gmail.com", encoded);
                    tenantService.saveTenant(tenant);
                    tenantService.saveTenant(tenant1);
                    tenantService.saveTenant(tenant2);

                    // Create houses
                    House house1 = new House(101);
                    house1.setHouseStatus(HouseStatus.VACANT);
                    House house2 = new House(102);
                    house2.setHouseStatus(HouseStatus.OCCUPIED);

                    // Create a list of houses
                    List<House> houses = new ArrayList<>();
                    houses.add(house1);
                    houses.add(house2);

                    // Create building and assign landlord
                    Building building = new Building("dimples", "g town", 10L, landlord, houses);
                    buildingService.save(building);

                    // Assign tenant to house1
                    house1.setTenant(tenant);
                    house1.setBuilding(building);
                    house2.setTenant(tenant1);
                    house2.setBuilding(building);

                    // Save houses
                    houseService.save(house1);
                    houseService.save(house2);

                    String password1 = "1234";
                    // Create sample buildings
                    List<Building> buildings = generateBuildings();
                    // Save buildings to the database
                    buildingRepository.saveAll(buildings);
                };
            }

            private List<Building> generateBuildings() {
                List<Building> buildings = new ArrayList<>();
                Random random = new Random();

                for (int i = 0; i < 42; i++) { // Generate 42 buildings
                    String buildingName = generateRandomBuildingName();
                    String address = generateRandomAddress();
                    long capacity = random.nextInt(20) + 1;
                    Building building = new Building(buildingName, address, capacity, null);
                    building.setHouses(generateHouses(building));
                    buildings.add(building);
                }
                return buildings;
            }

            private List<House> generateHouses(Building building) {
                List<House> houses = new ArrayList<>();
                for (int i = 0; i < 5; i++) { // Generate 5 houses for each building
                    int houseNumber = generateRandomHouseNumber();
                    House house = new House(houseNumber, building);
                    houses.add(house);
                }
                return houses;
            }

            private String generateRandomBuildingName() {
                String[] buildingNames = {"Alpha", "Beta", "Gamma", "Delta", "Epsilon"};
                Random random = new Random();
                return buildingNames[random.nextInt(buildingNames.length)];
            }

            private String generateRandomAddress() {
                String[] streets = {"Main Street", "Oak Avenue", "Maple Road", "Cedar Lane", "Elm Street"};
                String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Phoenix"};
                Random random = new Random();
                return random.nextInt(100) + " " + streets[random.nextInt(streets.length)] + ", " +
                        cities[random.nextInt(cities.length)];
            }

            private int generateRandomHouseNumber() {
                Random random = new Random();
                return random.nextInt(500) + 100;
            }
}
