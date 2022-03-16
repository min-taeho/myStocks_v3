package com.nuritech.stock.mystock.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TradingNoteRepository extends JpaRepository<TradingNote, Long> {
    /*
    @Query("SELECT p FROM TradingNote p")
    List<TradingNote> findAll();
    */

    List<TradingNote> findByEmailOrderByTradingDateDesc(String email);

    TradingNote findByStockAndPortfolioIdAndTradingDateAndTradingType(Stock stock, Long portfolioId, String tradingDate, String tradingType);
}
