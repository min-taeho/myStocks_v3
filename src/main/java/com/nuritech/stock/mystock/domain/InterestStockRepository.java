package com.nuritech.stock.mystock.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterestStockRepository extends JpaRepository<InterestStock, Long> {
    /*
    @Query("SELECT p FROM InterestStock p")
    List<InterestStock> findAll();
    */

    List<InterestStock> findByEmail(String email, Sort sort);
}
