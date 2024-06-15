package com.guava.E_HOSTELS.hostel.building;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.guava.E_HOSTELS.hostel.house.House;
import com.guava.E_HOSTELS.hostel.house.HouseStatus;
import com.guava.E_HOSTELS.users.landlord.Landlord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDTO {
    private Long buildingId;
    private String buildingName;

    private String area;
    private Long distance;
    private String photo;
    private int totalHouses;
    private List<House> houses;
    private Landlord landlord;


}
