package com.nuritech.stock.mystock.dto.portfolioStock;

import com.nuritech.stock.mystock.domain.Portfolio;
import com.nuritech.stock.mystock.domain.PortfolioStock;
import com.nuritech.stock.mystock.domain.Stock;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioSaveRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PortfolioStockSaveRequestDto {

    //private PortfolioSaveRequestDto portfolio;
    //private StockSaveRequestDto stock;
    private Long portfolioId;
    private String ticker;

    private Portfolio portfolio;
    private Stock stock;

    private Integer stockNum;
    private BigDecimal unitPrice;

    @Builder
    public PortfolioStockSaveRequestDto(Long portfolioId, String ticker, Portfolio portfolio, Stock stock, Integer stockNum, BigDecimal unitPrice) {
        this.portfolioId = portfolioId;
        this.ticker = ticker;
        this.portfolio = portfolio;
        this.stock = stock;
        this.stockNum = stockNum;
        this.unitPrice = unitPrice;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public PortfolioStock toEntity() {
        return PortfolioStock.builder()
                .portfolio(portfolio)
                .stock(stock)
                .stockNum(stockNum)
                .unitPrice(unitPrice)
                .build();
    }

}
