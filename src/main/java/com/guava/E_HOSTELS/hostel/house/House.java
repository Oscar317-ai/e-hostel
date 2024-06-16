package com.guava.E_HOSTELS.hostel.house;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.guava.E_HOSTELS.hostel.building.Building;
import com.guava.E_HOSTELS.users.tenant.Tenant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"building", "tenant"})
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long houseId;
    private int door_no;
    private int totalBookings;

    @Enumerated(EnumType.STRING)
    private HouseStatus houseStatus = HouseStatus.VACANT;

    private String photo;
    private int rentAmount;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    @JsonManagedReference
    private Tenant tenant;

    // Other rental information fields
    private LocalDate rentalStartDate = LocalDate.now();
    private LocalDate rentalEndDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "building_id")
    @JsonBackReference
    private Building building;


    public House(int door_no, Building building) {
        this.door_no = door_no;
        this.building = building;
    }

    public House(int door_no) {
        this.door_no = door_no;
    }


    public void updateBookings(int increment) {
        this.totalBookings += increment;
        if (this.building != null) {
            this.building.updateTotalDemand();
        }
    }
}

