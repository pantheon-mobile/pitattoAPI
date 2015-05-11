package com.pitatto.repository;

import java.util.List;

import com.pitatto.entity.IndustrialClassLEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IndustrialClassLRepository extends JpaRepository<IndustrialClassLEntity, Integer> {

    @Query("select classL from IndustrialClassLEntity classL order by classL.sort_order")
    List<IndustrialClassLEntity> getDivision();

}
