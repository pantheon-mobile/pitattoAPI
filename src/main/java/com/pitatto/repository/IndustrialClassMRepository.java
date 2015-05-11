package com.pitatto.repository;

import java.util.List;

import com.pitatto.entity.IndustrialClassMEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IndustrialClassMRepository extends JpaRepository<IndustrialClassMEntity, Integer> {
    @Query("select classM from IndustrialClassMEntity classM where classM.industrial_class_l_id = ?1 order by classM.sort_order")
    List<IndustrialClassMEntity> getDivision(int division);
}
