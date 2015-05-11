package com.pitatto.repository;

import java.util.List;

import com.pitatto.entity.PrefectureEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PrefectureRepository extends JpaRepository<PrefectureEntity, Integer> {
    @Query("select Pref from PrefectureEntity Pref where Pref.country_region_id = ?1 order by Pref.sort_order")
    List<PrefectureEntity> getArea(int area);
}
