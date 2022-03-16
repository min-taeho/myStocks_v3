package com.nuritech.stock.mystock.dto.interestStock;

import com.nuritech.stock.mystock.domain.InterestStock;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import lombok.Getter;

@Getter
public class InterestStockResponseDto {
    private Long interestStockId;
    private String email;

    private StockResponseDto stock;

    public InterestStockResponseDto(InterestStock entity) {
        this.interestStockId = entity.getId();
        this.email = entity.getEmail();
        this.stock = this.getStock(entity);
    }

    private StockResponseDto getStock(InterestStock entity) {
        if ( entity == null || entity.getStock() == null ) return null;
        return new StockResponseDto(entity.getStock());
    }
}
