package com.nuritech.stock.mystock.dto.portfolio;

import com.nuritech.stock.mystock.domain.Portfolio;
import com.nuritech.stock.mystock.domain.PortfolioStock;
import com.nuritech.stock.mystock.domain.Stock;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockResponseDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class PortfolioResponseDto {
    private Long portfolioId;
    private String email;
    private String name;

    private List<PortfolioStockResponseDto> portfolioStocks;

    public PortfolioResponseDto(Portfolio entity) {
        this.portfolioId = entity.getId();
        this.email = entity.getEmail();
        this.name = entity.getName();
        // 순환참조 제거
        //this.portfolioStocks = this.getPortfolioStocks(entity);
    }

    private List<PortfolioStockResponseDto> getPortfolioStocks(Portfolio entity) {
        if ( entity == null || entity.getPortfolioStocks() == null ) return null;
        return entity.getPortfolioStocks().stream()
                .map(PortfolioStockResponseDto::new)
                .collect(Collectors.toList());
    }
}
