package com.nuritech.stock.mystock.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query("SELECT p FROM Stock p")
    List<Stock> findAll();

    Stock findByTicker(String ticker);
}
