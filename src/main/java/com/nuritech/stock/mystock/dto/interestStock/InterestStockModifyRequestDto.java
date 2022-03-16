package com.nuritech.stock.mystock.dto.interestStock;

import com.nuritech.stock.mystock.domain.InterestStock;
import com.nuritech.stock.mystock.dto.stock.StockModifyRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InterestStockModifyRequestDto {

    private Long interestStockId;
    private String email;
    private StockModifyRequestDto stock;

    @Builder
    public InterestStockModifyRequestDto(Long interestStockId, String email, StockModifyRequestDto stock) {
        this.interestStockId = interestStockId;
        this.email = email;
        this.stock = stock;
    }


}
