package com.nuritech.stock.mystock.dto.scrap;

import com.google.gson.Gson;
import com.nuritech.stock.mystock.common.constant.ReferenceSiteConstants;
import com.nuritech.stock.mystock.common.util.ScrapUtils;
import com.nuritech.stock.mystock.domain.Stock;
import com.nuritech.stock.mystock.dto.stock.StockModifyRequestDto;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

@Getter
public class StockDto {
    private Long stockId;
    private String ticker;
    private String stockName;
    private String businessCycle;
    private String sector;
    private BigDecimal currentPrice;
    private BigDecimal highestPrice;
    private BigDecimal lowerPrice;
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

    private String[] etfTicker = {"QYLD", "JEPI", "NUSI", "SDIV", "KBWY", "HNDL", "SRET", "PGX", "SPHD", "SPYD"};
    private String[] monthlyTicker = {"O", "QYLD", "JEPI", "NUSI", "SDIV", "KBWY", "HNDL", "SRET", "PGX", "SPHD", "SPYD"};
    private String[] noDividend = {"SSY", "FB", "AMZN"};

    public StockDto(Stock entity) {
        this.stockId = entity.getId();
        this.ticker = entity.getTicker();
        this.stockName = entity.getStockName();
        this.businessCycle = entity.getBusinessCycle();
        this.sector = entity.getSector();
        this.currentPrice = entity.getCurrentPrice();
        this.highestPrice = entity.getHighestPrice();
        this.lowerPrice = entity.getLowerPrice();
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
    }

    public StockModifyRequestDto toStockModifyRequestDto() {
        return StockModifyRequestDto.builder()
                .stockId(stockId)
                .ticker(ticker)
                .stockName(stockName)
                .businessCycle(businessCycle)
                .sector(sector)
                .currentPrice(currentPrice)
                .highestPrice(highestPrice)
                .lowerPrice(lowerPrice)
                .nobilityStockYn(nobilityStockYn)
                .dividendYield(dividendYield)
                .dividendGrowth(dividendGrowth)
                .fiveyearGrowthRate(fiveyearGrowthRate)
                .payoutRatio(payoutRatio)
                .fiveyearAvgDividendYield(fiveyearAvgDividendYield)
                .nation(nation)
                .build();
    }

    public void setScrapInfo() {

        Gson gson = new Gson();
        
        // ETF
        if ( Arrays.stream(etfTicker).anyMatch(ticker::equals) ) {
            IexCloudStockPriceDto stockPriceDto = gson.fromJson(ScrapUtils.getStockPriceFromIexCloud(ticker).toString(),
                    IexCloudStockPriceDto.class);

            this.stockName = stockPriceDto.getCompanyName();
            this.currentPrice = stockPriceDto.getLatestPrice();
            this.highestPrice = stockPriceDto.getWeek52High();
            this.lowerPrice = stockPriceDto.getWeek52Low();
        }
        // 주식
        else {
            Map<String, Object> stockInfo = ScrapUtils.getStockInfoFromYahooFnnance(ticker);

            this.stockName = (String) stockInfo.get("stockNm");
            this.currentPrice = (BigDecimal) stockInfo.get("currentPrice");
            this.highestPrice = (BigDecimal) stockInfo.get("highestPrice");
            this.lowerPrice = (BigDecimal) stockInfo.get("lowerPrice");
            this.annualPayout = (BigDecimal) stockInfo.get("annualPayout");
            this.dividendYield = (BigDecimal) stockInfo.get("divYield");
            this.payoutRatio = (BigDecimal) stockInfo.get("payoutRatio");
            this.fiveyearAvgDividendYield = (BigDecimal) stockInfo.get("fiveYearAvgDividendYield");

        }


    }




}
