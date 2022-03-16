package com.nuritech.stock.mystock.dto.portfolio;

import com.nuritech.stock.mystock.domain.Portfolio;
import com.nuritech.stock.mystock.domain.Stock;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class PortfolioSaveRequestDto {

    private String email;
    private String name;

    @Builder
    public PortfolioSaveRequestDto(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public Portfolio toEntity() {
        return Portfolio.builder()
                .email(email)
                .name(name)
                .build();
    }
}
