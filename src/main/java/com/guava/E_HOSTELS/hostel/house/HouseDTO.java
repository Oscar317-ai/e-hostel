package com.guava.E_HOSTELS.hostel.house;

import com.guava.E_HOSTELS.hostel.building.Building;
import com.guava.E_HOSTELS.users.tenant.Tenant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseDTO {

    private Long houseId;
    private int door_no;
    private HouseStatus houseStatus;
    private String photo;
    private int rentAmount;
    private Tenant tenant;
    private LocalDate rentalStartDate;
    private LocalDate rentalEndDate;
    private Building building;
}
