package com.pitatto.repository;

import java.util.List;

import com.pitatto.entity.CountryRegionEntity;
import com.pitatto.entity.PrefectureEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountryRegionRepository extends JpaRepository<CountryRegionEntity, Integer> {
    @Query("select CountryRegion from CountryRegionEntity CountryRegion order by CountryRegion.sort_order")
    List<CountryRegionEntity> getArea();
}
