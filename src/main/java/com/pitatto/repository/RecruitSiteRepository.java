package com.pitatto.repository;

import java.util.List;

import com.pitatto.entity.RecruitSiteEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecruitSiteRepository extends JpaRepository<RecruitSiteEntity, Integer> {
}
