package com.guava.E_HOSTELS.hostel.building;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.guava.E_HOSTELS.hostel.house.House;
import com.guava.E_HOSTELS.hostel.house.HouseStatus;
import com.guava.E_HOSTELS.users.landlord.Landlord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "houses")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buildingId;
    private String buildingName;
    private int totalDemand;
    private int standardRent;



    private String area;
    private Long distance;
    private String photo;
    private int totalHouses;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<House> houses;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

    public Building(String buildingName, String area, Long distance, List<House> houses) {
        this.buildingName = buildingName;
        this.area = area;
        this.distance = distance;
        this.houses = houses;
    }

    // Parameterized constructor
    public Building(String buildingName, String area, Long distance) {
        this.buildingName = buildingName;
        this.area = area;
        this.distance = distance;
    }

    public Building(String buildingName, String area, Long distance, Landlord landlord, List<House> houses) {
        this.buildingName = buildingName;
        this.area = area;
        this.distance = distance;
        this.landlord = landlord;
        this.houses = houses;
    }



    public int countVacantHouses() {
        if (houses == null) {
            System.out.println("there are no houses");
            return 0;
        }
        return (int) houses.stream()
                .filter(house -> house.getHouseStatus() == HouseStatus.VACANT)
                .count();
    }

    public void updateTotalDemand() {
        if (houses != null) {
            totalDemand = houses.stream()
                    .mapToInt(House::getTotalBookings)
                    .sum();
        }
    }



    // toString method
    @Override
    public String toString() {
        return "Building{" +
                ", buildingName='" + buildingName + '\'' +
                ", area='" + area + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }


}

