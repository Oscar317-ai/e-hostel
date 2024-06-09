package com.guava.E_HOSTELS.hostel.house;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {


    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<House> findVacantHouses() {
        return houseRepository.findByHouseStatus(HouseStatus.VACANT);
    }


    public House save(House house) {
        return houseRepository.save(house);
    }

    public House findById(Long id) {
        return houseRepository.findById(id).orElse(null);
    }

    public List<House> findAll() {
        return houseRepository.findAll();
    }

    public List<House> findHousesByTenantId(Long tenantId) {
        return houseRepository.findHousesByTenant_TenantId(tenantId);
    }

    public void deleteHouse(Long houseId) {
        houseRepository.deleteById(houseId);
    }

    public void updateHouse(Long houseId, House updatedHouse) {
        House houseToUpdate = houseRepository.findById(houseId).orElse(null);
        if (houseToUpdate != null) {
            houseToUpdate.setDoor_no(updatedHouse.getDoor_no());
            houseToUpdate.setPhoto(updatedHouse.getPhoto());
            houseToUpdate.setRentAmount(updatedHouse.getRentAmount());
            houseToUpdate.setTenant(updatedHouse.getTenant());
            houseToUpdate.setRentalStartDate(updatedHouse.getRentalStartDate());
            houseToUpdate.setRentalEndDate(updatedHouse.getRentalEndDate());
            // Update other fields as needed
            houseRepository.save(houseToUpdate);
        }

    }
}

