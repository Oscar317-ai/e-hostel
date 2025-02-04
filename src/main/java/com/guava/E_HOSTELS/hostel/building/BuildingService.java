package com.guava.E_HOSTELS.hostel.building;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;


    public Page<Building> searchByNameOrArea(String buildingName,String area, Pageable pageable ){

        Page<Building> mine = buildingRepository.findByBuildingNameContainingIgnoreCaseOrAreaContainingIgnoreCase(buildingName,area,pageable);

        System.out.println(List.of(mine));

        System.out.println(List.of(mine.getContent()));

        return buildingRepository.findByBuildingNameContainingIgnoreCaseOrAreaContainingIgnoreCase( buildingName, area, pageable);

    }


    public Page<Building> searchBuildings(String area, Long maxDistance, Integer maxPrice, String buildingName, Pageable pageable) {
        return buildingRepository.findByCriteria(area, maxDistance, maxPrice, buildingName, pageable);
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

    public void updateBuilding(Long buildingId, Building updatedBuilding) {
        Building buildingToUpdate = buildingRepository.findById(buildingId).orElse(null);

        if (buildingToUpdate != null){
            if (updatedBuilding.getBuildingName() != null){
                buildingToUpdate.setBuildingName(updatedBuilding.getBuildingName());
            }
            if (updatedBuilding.getDistance()!= null){
                buildingToUpdate.setDistance(updatedBuilding.getDistance());
            }
            if (updatedBuilding.getArea() != null){
                buildingToUpdate.setArea(updatedBuilding.getArea());
            }if (updatedBuilding.getPhoto() != null){
                buildingToUpdate.setPhoto(updatedBuilding.getPhoto());
            }
            if (updatedBuilding.getStandardRent() > 0){
                buildingToUpdate.setStandardRent(updatedBuilding.getStandardRent());
            }
        }
        buildingRepository.save(buildingToUpdate);
    }
}
