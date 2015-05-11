package com.pitatto.repository;

import java.util.List;

import com.pitatto.entity.CompanyEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {
}
