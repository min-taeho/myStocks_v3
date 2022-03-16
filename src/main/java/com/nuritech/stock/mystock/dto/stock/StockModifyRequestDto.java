package com.nuritech.stock.mystock.dto.stock;

import com.nuritech.stock.mystock.domain.Stock;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class StockModifyRequestDto {

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

    @Builder
    public StockModifyRequestDto(Long stockId, String ticker, String stockName, String businessCycle, String sector, BigDecimal currentPrice, BigDecimal highestPrice, BigDecimal lowerPrice, BigDecimal desiredPurchasePrice, String nobilityStockYn, String dividendPayMonth, String companyInfo, BigDecimal annualPayout, BigDecimal dividendYield, BigDecimal dividendGrowth, BigDecimal fiveyearGrowthRate, BigDecimal payoutRatio, BigDecimal fiveyearAvgDividendYield, String nation) {
        this.stockId = stockId;
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
    }

    public Stock toEntity() {
        return Stock.builder()
                .ticker(ticker)
                .stockName(stockName)
                .businessCycle(businessCycle)
                .sector(sector)
                .currentPrice(currentPrice)
                .highestPrice(highestPrice)
                .lowerPrice(lowerPrice)
                .desiredPurchasePrice(desiredPurchasePrice)
                .nobilityStockYn(nobilityStockYn)
                .dividendYield(dividendYield)
                .dividendGrowth(dividendGrowth)
                .fiveyearGrowthRate(fiveyearGrowthRate)
                .payoutRatio(payoutRatio)
                .fiveyearAvgDividendYield(fiveyearAvgDividendYield)
                .nation(nation)
                .build();
    }

    @Override
    public String toString() {
        return "StockModifyRequestDto{" +
                "stockId=" + stockId +
                ", ticker='" + ticker + '\'' +
                ", stockName='" + stockName + '\'' +
                ", businessCycle='" + businessCycle + '\'' +
                ", sector='" + sector + '\'' +
                ", currentPrice=" + currentPrice +
                ", highestPrice=" + highestPrice +
                ", lowerPrice=" + lowerPrice +
                ", desiredPurchasePrice=" + desiredPurchasePrice +
                ", nobilityStockYn='" + nobilityStockYn + '\'' +
                ", dividendPayMonth='" + dividendPayMonth + '\'' +
                ", companyInfo='" + companyInfo + '\'' +
                ", annualPayout=" + annualPayout +
                ", dividendYield=" + dividendYield +
                ", dividendGrowth=" + dividendGrowth +
                ", fiveyearGrowthRate=" + fiveyearGrowthRate +
                ", payoutRatio=" + payoutRatio +
                ", fiveyearAvgDividendYield=" + fiveyearAvgDividendYield +
                ", nation='" + nation + '\'' +
                '}';
    }
}
