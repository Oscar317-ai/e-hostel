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


        @Query("SELECT b FROM Building b WHERE "
                + "(LOWER(b.area) LIKE LOWER(CONCAT('%', :area, '%')) OR :area IS NULL) AND "
                + "(b.distance <= :maxDistance OR :maxDistance IS NULL) AND "
                + "(b.standardRent <= :maxPrice OR :maxPrice IS NULL) AND "
                + "(LOWER(b.buildingName) LIKE LOWER(CONCAT('%', :buildingName, '%')) OR :buildingName IS NULL)")
        Page<Building> findByCriteria(@Param("area") String area,
                                      @Param("maxDistance") Long maxDistance,
                                      @Param("maxPrice") Integer maxPrice,
                                      @Param("buildingName") String buildingName,
                                      Pageable pageable);

        @Query("SELECT b FROM Building b LEFT JOIN FETCH b.landlord WHERE b.buildingId = :buildingId")
        Building findWithLandlordById(@Param("buildingId") Long buildingId);

        @Query("SELECT b FROM Building b LEFT JOIN FETCH b.landlord")
        Page<Building> findAllWithLandlord(Pageable pageable);
    }

