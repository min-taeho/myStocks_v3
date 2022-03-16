package com.nuritech.stock.mystock.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    /*
    @Query("SELECT p FROM Portfolio p")
    List<Portfolio> findAll();
     */

    List<Portfolio> findByEmail(String email);

    Portfolio findByEmailAndName(String email, String name);

    Portfolio findByIdAndEmail(Long id, String email);
}
