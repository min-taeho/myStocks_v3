package com.nuritech.stock.mystock.dto.portfolioStock;

import com.nuritech.stock.mystock.domain.InterestStock;
import com.nuritech.stock.mystock.domain.Portfolio;
import com.nuritech.stock.mystock.domain.PortfolioStock;
import com.nuritech.stock.mystock.domain.Stock;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PortfolioStockResponseDto {

    private Long portfolioStockId;
    private PortfolioResponseDto portfolio;
    private StockResponseDto stock;
    private Integer stockNum;
    private BigDecimal unitPrice;

    //private String businessCycle;
    //private String sector;
    //private String ticker;
    //private BigDecimal currentPrice;
    private BigDecimal declineRate;
    //private BigDecimal lowerPrice;
    //private BigDecimal highestPrice;
    private BigDecimal totalTradingAmount;
    private BigDecimal evalAmount;
    private BigDecimal earningAmount;
    private BigDecimal earningRate;
    //private BigDecimal annualPayout;
    private BigDecimal totalPayout;
    private BigDecimal investmentDivYield;
    //private String dividendPayMonth;

    public PortfolioStockResponseDto(PortfolioStock entity) {
        this.portfolioStockId = entity.getId();
        this.portfolio = this.getPortfolio(entity);
        this.stock = this.getStock(entity);
        this.stockNum = entity.getStockNum();
        this.unitPrice = entity.getUnitPrice();
        this.totalTradingAmount = entity.getTotalTradingAmount();
        this.evalAmount = entity.getEvalAmount();
        this.earningAmount = entity.getEarningAmount();
        this.earningRate = entity.getEarningRate();
        this.totalPayout = entity.getTotalPayout();
        this.investmentDivYield = entity.getInvestmentDivYield();
    }

    private PortfolioResponseDto getPortfolio(PortfolioStock entity) {
        if ( entity == null || entity.getPortfolio() == null ) return null;
        return new PortfolioResponseDto(entity.getPortfolio());
    }

    private StockResponseDto getStock(PortfolioStock entity) {
        if ( entity == null || entity.getStock() == null ) return null;
        return new StockResponseDto(entity.getStock());
    }

    @Override
    public String toString() {
        return "PortfolioStockResponseDto{" +
                "portfolioStockId=" + portfolioStockId +
                ", portfolio=" + portfolio.toString() +
                ", stock=" + stock.toString() +
                ", stockNum=" + stockNum +
                ", unitPrice=" + unitPrice +
                ", declineRate=" + declineRate +
                ", totalTradingAmount=" + totalTradingAmount +
                ", evalAmount=" + evalAmount +
                ", earningAmount=" + earningAmount +
                ", earningRate=" + earningRate +
                ", totalPayout=" + totalPayout +
                ", investmentDivYield=" + investmentDivYield +
                '}';
    }
}
