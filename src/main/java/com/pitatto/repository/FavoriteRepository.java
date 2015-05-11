package com.pitatto.repository;


import com.pitatto.entity.FavoriteEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Integer> {

}
