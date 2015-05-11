package com.pitatto.repository;

import com.pitatto.entity.CompanyAddressEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyAddressRepository extends JpaRepository<CompanyAddressEntity, Integer> {

    @Query("select C.address_1 from CompanyAddressEntity C where C.company_id = ?1 and C.headquater_assort = 1")
    String getArea(int companyId);

}
