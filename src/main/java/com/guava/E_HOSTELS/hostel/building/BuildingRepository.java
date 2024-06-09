package com.guava.E_HOSTELS.hostel.building;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
    Page<Building> findByBuildingNameContainingIgnoreCaseOrAreaContainingIgnoreCase(String buildingName, String area, Pageable pageable);

        @Query("SELECT b FROM Building b LEFT JOIN FETCH b.landlord WHERE b.buildingId = :buildingId")
        Building findWithLandlordById(@Param("buildingId") Long buildingId);

        // Add pagination support for retrieving buildings with landlord details
        @Query("SELECT b FROM Building b LEFT JOIN FETCH b.landlord")
        Page<Building> findAllWithLandlord(Pageable pageable);

}
