package com.guava.E_HOSTELS.utilities;

import com.guava.E_HOSTELS.hostel.building.Building;
import com.guava.E_HOSTELS.hostel.building.BuildingDTO;
import com.guava.E_HOSTELS.hostel.house.House;
import com.guava.E_HOSTELS.hostel.house.HouseDTO;

import java.util.HashMap;
import java.util.Map;

public class EntityMapper  {

    public static HouseDTO toHouseDTO(House house){
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setHouseId(house.getHouseId());
        houseDTO.setHouseStatus(house.getHouseStatus());
        houseDTO.setBuilding(house.getBuilding());
        houseDTO.setPhoto(house.getPhoto());
        houseDTO.setDoor_no(house.getDoor_no());
        houseDTO.setTenant(house.getTenant());
        houseDTO.setRentAmount(house.getRentAmount());
        houseDTO.setRentalStartDate(house.getRentalStartDate());
        houseDTO.setRentalEndDate(house.getRentalEndDate());

        return  houseDTO;
    }

    public static BuildingDTO buildingDTO(Building building){
        BuildingDTO buildingDTO = new BuildingDTO();
        buildingDTO.setBuildingName(building.getBuildingName());
        buildingDTO.setBuildingId(building.getBuildingId());
        buildingDTO.setArea(building.getArea());
        buildingDTO.setHouseStatus(building.getHouseStatus());
        buildingDTO.setHouses(building.getHouses());
        buildingDTO.setDistance(building.getDistance());
        buildingDTO.setLandlord(building.getLandlord());
        buildingDTO.setPhoto(building.getPhoto());

        return buildingDTO;
    }

    public static Map<String, Object> toHouseDetailsMap(House house) {
        Map<String, Object> houseDetails = new HashMap<>();
        houseDetails.put("houseId", house.getHouseId());
        houseDetails.put("door_no", house.getDoor_no());
        houseDetails.put("photo", house.getPhoto()); // Assuming photo is a URL
        houseDetails.put("occupied", house.getHouseStatus().equals("OCCUPIED"));
        houseDetails.put("rentalStartDate", house.getRentalStartDate());
        houseDetails.put("rentalEndDate", house.getRentalEndDate());
        // Add other relevant details as needed
        return houseDetails;
    }
}
