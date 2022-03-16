package com.nuritech.stock.mystock.domain;

import com.nuritech.stock.mystock.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="TRADING_NOTE")
@Getter
@NoArgsConstructor
public class TradingNote extends BaseTimeEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String email;

    /*
    @ManyToOne
    @JoinColumn(name = "PORTFOLIO_STOCKS_ID")
    private PortfolioStock portfolioStock;
    */
    @ManyToOne
    @JoinColumn(name = "STOCK_ID")
    private Stock stock;

    @Column
    private Long portfolioId;

    @Column
    private String tradingDate;

    @Column
    private String tradingType;

    @Column
    private Integer stockNum;

    @Column
    private BigDecimal unitPrice;

    @Column
    private BigDecimal tradingAmount;

    @Column
    private BigDecimal fee;

    @Column
    private BigDecimal exchangeRate;

    @Column
    private String nation;

    @Builder
    public TradingNote(String email,
                       Stock stock,
                       Long portfolioId,
                       String tradingDate,
                       String tradingType,
                       Integer stockNum,
                       BigDecimal unitPrice,
                       BigDecimal tradingAmount,
                       BigDecimal fee,
                       BigDecimal exchangeRate,
                       String nation) {
        this.email = email;
        this.stock = stock;
        this.portfolioId = portfolioId;
        this.tradingDate = tradingDate;
        this.tradingType = tradingType;
        this.stockNum = stockNum;
        this.unitPrice = unitPrice;
        this.tradingAmount = tradingAmount;
        this.fee = fee;
        this.exchangeRate = exchangeRate;
        this.nation = nation;
    }

    public void update(String email,
                       Stock stock,
                       Long portfolioId,
                       String tradingDate,
                       String tradingType,
                       Integer stockNum,
                       BigDecimal unitPrice,
                       BigDecimal tradingAmount,
                       BigDecimal fee,
                       BigDecimal exchangeRate,
                       String nation) {
        this.email = email;
        this.stock = stock;
        this.portfolioId = portfolioId;
        this.tradingDate = tradingDate;
        this.tradingType = tradingType;
        this.stockNum = stockNum;
        this.unitPrice = unitPrice;
        this.tradingAmount = tradingAmount;
        this.fee = fee;
        this.exchangeRate = exchangeRate;
        this.nation = nation;
    }


}
