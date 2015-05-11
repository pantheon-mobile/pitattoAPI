package com.pitatto.repository;

import com.pitatto.entity.CompanyIndustrialClassEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyIndustrialClassRepository extends JpaRepository<CompanyIndustrialClassEntity, Integer> {

    @Query("select C.company_industrial_class_note from CompanyIndustrialClassEntity C where C.company_id = ?1 and C.main_industrial_class_flag = 1")
    String getCategory(int companyId);

}
