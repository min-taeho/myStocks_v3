package com.nuritech.stock.mystock.dto.interestStock;

import com.nuritech.stock.mystock.domain.InterestStock;
import com.nuritech.stock.mystock.domain.Portfolio;
import com.nuritech.stock.mystock.domain.Stock;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterestStockSaveRequestDto {

    private String email;
    private String ticker;
    private Stock stock;

    @Builder
    public InterestStockSaveRequestDto(String email, String ticker) {
        this.email = email;
        this.ticker = ticker;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public InterestStock toEntity() {
        return InterestStock.builder()
                .email(email)
                .stock(stock)
                .build();
    }
}
