package com.nuritech.stock.mystock.domain;

import com.nuritech.stock.mystock.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
@Table(name="STOCK")
public class Stock extends BaseTimeEntity {

    @Id
    @Column(name = "STOCK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String ticker;

    @Column
    private String stockName;

    @Column
    private String businessCycle;

    @Column
    private String sector;

    @Column
    private BigDecimal currentPrice;

    @Column
    private BigDecimal highestPrice;

    @Column
    private BigDecimal lowerPrice;

    @Column
    private BigDecimal desiredPurchasePrice;

    @Column
    private String nobilityStockYn;

    @Column
    private String dividendPayMonth;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String companyInfo;

    @Column
    private BigDecimal annualPayout;

    @Column
    private BigDecimal dividendYield;

    @Column
    private BigDecimal dividendGrowth;

    @Column
    private BigDecimal fiveyearGrowthRate;

    @Column
    private BigDecimal payoutRatio;

    @Column
    private BigDecimal fiveyearAvgDividendYield;

    @Column
    private String nation;

    @Column
    private BigDecimal declineRate;

    @Builder
    public Stock(String ticker, String stockName, String businessCycle, String sector, BigDecimal currentPrice, BigDecimal highestPrice, BigDecimal lowerPrice, BigDecimal desiredPurchasePrice, String nobilityStockYn, String dividendPayMonth, String companyInfo, BigDecimal annualPayout, BigDecimal dividendYield, BigDecimal dividendGrowth, BigDecimal fiveyearGrowthRate, BigDecimal payoutRatio, BigDecimal fiveyearAvgDividendYield, String nation) {
        this.ticker = ticker;
        this.stockName = stockName;
        this.businessCycle = businessCycle;
        this.sector = sector;
        this.currentPrice = currentPrice;
        this.highestPrice = highestPrice;
        this.lowerPrice = lowerPrice;
        this.desiredPurchasePrice = desiredPurchasePrice;
        this.nobilityStockYn = nobilityStockYn;
        this.dividendPayMonth = dividendPayMonth;
        this.companyInfo = companyInfo;
        this.annualPayout = annualPayout;
        this.dividendYield = dividendYield;
        this.dividendGrowth = dividendGrowth;
        this.fiveyearGrowthRate = fiveyearGrowthRate;
        this.payoutRatio = payoutRatio;
        this.fiveyearAvgDividendYield = fiveyearAvgDividendYield;
        this.nation = nation;
        this.setRefField();
    }

    public void update(String ticker, String stockName, String businessCycle, String sector, BigDecimal currentPrice, BigDecimal highestPrice, BigDecimal lowerPrice, BigDecimal desiredPurchasePrice, String nobilityStockYn, String dividendPayMonth, String companyInfo, BigDecimal annualPayout, BigDecimal dividendYield, BigDecimal dividendGrowth, BigDecimal fiveyearGrowthRate, BigDecimal payoutRatio, BigDecimal fiveyearAvgDividendYield, String nation) {
        this.ticker = ticker;
        this.stockName = stockName;
        this.businessCycle = businessCycle;
        this.sector = sector;
        this.currentPrice = currentPrice;
        this.highestPrice = highestPrice;
        this.lowerPrice = lowerPrice;
        this.desiredPurchasePrice = desiredPurchasePrice;
        this.nobilityStockYn = nobilityStockYn;
        this.dividendPayMonth = dividendPayMonth;
        this.companyInfo = companyInfo;
        this.annualPayout = annualPayout;
        this.dividendYield = dividendYield;
        this.dividendGrowth = dividendGrowth;
        this.fiveyearGrowthRate = fiveyearGrowthRate;
        this.payoutRatio = payoutRatio;
        this.fiveyearAvgDividendYield = fiveyearAvgDividendYield;
        this.nation = nation;
        this.setRefField();
    }

    private void setRefField() {
        if (ObjectUtils.isNotEmpty(this.currentPrice) && ObjectUtils.isNotEmpty(this.highestPrice)) {
            this.declineRate = (this.currentPrice.subtract(this.highestPrice)).divide(this.highestPrice, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        }
    }
}
