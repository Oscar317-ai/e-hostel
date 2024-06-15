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
            if (updatedHouse.getDoor_no() > 0){
                houseToUpdate.setDoor_no(updatedHouse.getDoor_no());
            }
            if (updatedHouse.getHouseStatus() != null){
                houseToUpdate.setHouseStatus(updatedHouse.getHouseStatus());
            }
            if (updatedHouse.getPhoto() != null){
                houseToUpdate.setPhoto(updatedHouse.getPhoto());
            }
            if (updatedHouse.getRentAmount() > 0){
                houseToUpdate.setRentAmount(updatedHouse.getRentAmount());
            }

            if (updatedHouse.getRentalStartDate() != null){
                houseToUpdate.setRentalStartDate(updatedHouse.getRentalStartDate());
            }
            if (updatedHouse.getRentalEndDate() != null){
                houseToUpdate.setRentalEndDate(updatedHouse.getRentalEndDate());
            }


            houseRepository.save(houseToUpdate);
        }

    }
}

