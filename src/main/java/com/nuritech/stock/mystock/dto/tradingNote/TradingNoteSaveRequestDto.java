package com.nuritech.stock.mystock.dto.tradingNote;

import com.nuritech.stock.mystock.domain.Portfolio;
import com.nuritech.stock.mystock.domain.PortfolioStock;
import com.nuritech.stock.mystock.domain.Stock;
import com.nuritech.stock.mystock.domain.TradingNote;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class TradingNoteSaveRequestDto {

    private String email;
    private Stock stock;
    private Long portfolioId;
    private String ticker;
    private String tradingDate;
    private String tradingType;
    private Integer stockNum;
    private BigDecimal unitPrice;
    private BigDecimal tradingAmount;
    private BigDecimal fee;
    private BigDecimal exchangeRate;
    private String nation;

    @Builder
    public TradingNoteSaveRequestDto(String email, Long portfolioId, String ticker, String tradingDate, String tradingType, Integer stockNum, BigDecimal unitPrice, BigDecimal tradingAmount, BigDecimal fee, BigDecimal exchangeRate, String nation) {
        this.email = email;
        //this.portfolioStock = portfolioStock;
        this.portfolioId = portfolioId;
        this.ticker = ticker;
        this.tradingDate = tradingDate;
        this.tradingType = tradingType;
        this.stockNum = stockNum;
        this.unitPrice = unitPrice;
        this.tradingAmount = tradingAmount;
        this.fee = fee;
        this.exchangeRate = exchangeRate;
        this.nation = nation;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public TradingNote toEntity() {
        return TradingNote.builder()
                .email(email)
                .stock(stock)
                .portfolioId(portfolioId)
                .tradingDate(tradingDate)
                .tradingType(tradingType)
                .stockNum(stockNum)
                .unitPrice(unitPrice)
                .tradingAmount(tradingAmount)
                .fee(fee)
                .exchangeRate(exchangeRate)
                .nation(nation)
                .build();
    }

    @Override
    public String toString() {
        return "TradingNoteSaveRequestDto{" +
                "email='" + email + '\'' +
                ", portfolioId=" + portfolioId +
                ", ticker='" + ticker + '\'' +
                ", tradingDate='" + tradingDate + '\'' +
                ", tradingType='" + tradingType + '\'' +
                ", stockNum=" + stockNum +
                ", unitPrice=" + unitPrice +
                ", tradingAmount=" + tradingAmount +
                ", fee=" + fee +
                ", exchangeRate=" + exchangeRate +
                ", nation='" + nation + '\'' +
                '}';
    }
}
