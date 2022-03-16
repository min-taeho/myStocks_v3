package com.nuritech.stock.mystock.dto.portfolio;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PortfolioModifyRequestDto {

    private Long portfolioId;
    private String email;
    private String name;

    @Builder
    public PortfolioModifyRequestDto(Long portfolioId, String email, String name) {
        this.portfolioId = portfolioId;
        this.email = email;
        this.name = name;
    }

}
