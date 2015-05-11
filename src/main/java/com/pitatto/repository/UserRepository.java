package com.pitatto.repository;


import com.pitatto.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public UserEntity getByUserLoginCode(String loginId);

    public UserEntity getByMemberId(int id);

}
