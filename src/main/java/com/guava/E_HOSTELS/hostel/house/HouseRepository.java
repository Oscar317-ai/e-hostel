package com.guava.E_HOSTELS.hostel.house;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findByHouseStatus(HouseStatus houseStatus);

    List<House> findHousesByTenant_TenantId(Long tenantId);
}

