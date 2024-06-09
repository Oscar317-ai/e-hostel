package com.guava.E_HOSTELS.hostel.building;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;


    public Page<Building> searchByNameOrArea(String buildingName,String area, Pageable pageable ){

        Page<Building> mine = buildingRepository.findByBuildingNameContainingIgnoreCaseOrAreaContainingIgnoreCase(buildingName,area,pageable);

        System.out.println(List.of(mine));

        System.out.println(List.of(mine.getContent()));

        return buildingRepository.findByBuildingNameContainingIgnoreCaseOrAreaContainingIgnoreCase( buildingName, area, pageable);

    }


    public Building createBuilding(Building building) {
        return buildingRepository.save(building);
    }

    public Optional<Building> searchBuildingById(Long buildingId){
        return buildingRepository.findById(buildingId);
    }


    public Building save(Building building) {
        return buildingRepository.save(building);
    }

    public Building findById(Long id) {
        return buildingRepository.findById(id).orElse(null);
    }

    public List<Building> findAll() {
        return buildingRepository.findAll();
    }
}
