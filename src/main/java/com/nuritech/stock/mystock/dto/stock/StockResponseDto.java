package com.nuritech.stock.mystock.dto.stock;

import com.nuritech.stock.mystock.domain.Stock;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class StockResponseDto {
    private Long stockId;
    private String ticker;
    private String stockName;
    private String businessCycle;
    private String sector;
    private BigDecimal currentPrice;
    private BigDecimal highestPrice;
    private BigDecimal lowerPrice;
    private BigDecimal desiredPurchasePrice;
    private String nobilityStockYn;
    private String dividendPayMonth;
    private String companyInfo;
    private BigDecimal annualPayout;
    private BigDecimal dividendYield;
    private BigDecimal dividendGrowth;
    private BigDecimal fiveyearGrowthRate;
    private BigDecimal payoutRatio;
    private BigDecimal fiveyearAvgDividendYield;
    private String nation;
    private BigDecimal declineRate;

    public StockResponseDto(Stock entity) {
        this.stockId = entity.getId();
        this.ticker = entity.getTicker();
        this.stockName = entity.getStockName();
        this.businessCycle = entity.getBusinessCycle();
        this.sector = entity.getSector();
        this.currentPrice = entity.getCurrentPrice();
        this.highestPrice = entity.getHighestPrice();
        this.lowerPrice = entity.getLowerPrice();
        this.desiredPurchasePrice = entity.getDesiredPurchasePrice();
        this.nobilityStockYn = entity.getNobilityStockYn();
        this.dividendPayMonth = entity.getDividendPayMonth();
        this.companyInfo = entity.getCompanyInfo();
        this.annualPayout = entity.getAnnualPayout();
        this.dividendYield = entity.getDividendYield();
        this.dividendGrowth = entity.getDividendGrowth();
        this.fiveyearGrowthRate = entity.getFiveyearGrowthRate();
        this.payoutRatio = entity.getPayoutRatio();
        this.fiveyearAvgDividendYield = entity.getFiveyearAvgDividendYield();
        this.nation = entity.getNation();
        this.declineRate = entity.getDeclineRate();
    }


}
