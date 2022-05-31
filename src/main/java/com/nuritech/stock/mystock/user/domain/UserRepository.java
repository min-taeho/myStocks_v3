package com.nuritech.stock.mystock.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findFirstByAuthId(String authId);
    Optional<UserEntity> findFirstByUserId(Long userId);

    @Query("SELECT p FROM UserEntity p")
    List<UserEntity> findAll();

}