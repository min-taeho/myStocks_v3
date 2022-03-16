package com.nuritech.stock.mystock.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortfolioStockRepository extends JpaRepository<PortfolioStock, Long> {
    @Query("SELECT p FROM PortfolioStock p")
    List<PortfolioStock> findAll();

    PortfolioStock findByStockAndPortfolio(Stock stock, Portfolio portfolio);

    List<PortfolioStock> findByPortfolio(Portfolio portfolio, Sort sort);

    List<PortfolioStock> findByStock(Stock stock);

}
