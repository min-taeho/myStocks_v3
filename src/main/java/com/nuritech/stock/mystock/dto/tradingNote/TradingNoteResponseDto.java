package com.nuritech.stock.mystock.dto.tradingNote;

import com.nuritech.stock.mystock.domain.PortfolioStock;
import com.nuritech.stock.mystock.domain.TradingNote;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockResponseDto;
import com.nuritech.stock.mystock.dto.scrap.StockDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TradingNoteResponseDto {

    private Long tradingNoteId;
    private String email;
    private StockResponseDto stock;
    private Long portfolioId;
    private String tradingDate;
    private String tradingType;
    private Integer stockNum;
    private BigDecimal unitPrice;
    private BigDecimal tradingAmount;
    private BigDecimal fee;
    private BigDecimal exchangeRate;
    private String nation;

    public TradingNoteResponseDto(TradingNote entity) {
        this.tradingNoteId = entity.getId();
        this.email = entity.getEmail();
        this.stock = this.getStock(entity);
        this.portfolioId = entity.getPortfolioId();
        this.tradingDate = entity.getTradingDate();
        this.tradingType = entity.getTradingType();
        this.stockNum = entity.getStockNum();
        this.unitPrice = entity.getUnitPrice();
        this.tradingAmount = entity.getTradingAmount();
        this.fee = entity.getFee();
        this.exchangeRate = entity.getExchangeRate();
        this.nation = entity.getNation();
    }

    private StockResponseDto getStock(TradingNote entity) {
        if ( entity == null || entity.getStock() == null ) return null;
        return new StockResponseDto(entity.getStock());
    }

    @Override
    public String toString() {
        return "TradingNoteResponseDto{" +
                "tradingNoteId=" + tradingNoteId +
                ", email='" + email + '\'' +
                ", portfolioId=" + portfolioId +
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
