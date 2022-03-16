package com.nuritech.stock.mystock.dto.portfolioStock;

import com.nuritech.stock.mystock.domain.Portfolio;
import com.nuritech.stock.mystock.domain.Stock;
import com.nuritech.stock.mystock.dto.stock.StockModifyRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PortfolioStockModifyRequestDto {

    private Long portfolioStockId;
    private Portfolio portfolio;
    private Stock stock;
    private Integer stockNum;
    private BigDecimal unitPrice;

    @Builder
    public PortfolioStockModifyRequestDto(Long portfolioStockId, Portfolio portfolio, Stock stock, Integer stockNum, BigDecimal unitPrice) {
        this.portfolioStockId = portfolioStockId;
        this.portfolio = portfolio;
        this.stock = stock;
        this.stockNum = stockNum;
        this.unitPrice = unitPrice;
    }
}
