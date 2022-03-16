package com.nuritech.stock.mystock.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nuritech.stock.mystock.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
@Table(name="PORTFOLIO_STOCK")
@Getter
@NoArgsConstructor
public class PortfolioStock extends BaseTimeEntity {

    @Id
    @Column(name = "PORTFOLIO_STOCK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PORTFOLIO_ID")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "STOCK_ID")
    private Stock stock;

    @Column
    private Integer stockNum;

    @Column
    private BigDecimal unitPrice;

    /* Stock 정보를 이용해서 화면에 출력하는 가공된 필드
       View 단에서 가공할 수도 있으나, 정렬을 위해 domain 단에서 가공하여 저장
       JPA Sort를 이용하여 서버측 정렬 후 View에 리턴 한다.
       부트스트랩 b-table에서 javascript 단 정렬 기능을 제공하고 있으나,
       오류나 화면 조작이 custom 하기 까다로워서 서버측 정렬로 구현 함
     */
    @Column
    private BigDecimal totalTradingAmount;

    @Column
    private BigDecimal evalAmount;

    @Column
    private BigDecimal earningAmount;

    @Column
    private BigDecimal earningRate;

    @Column
    private BigDecimal totalPayout;

    @Column
    private BigDecimal investmentDivYield;

    @Builder
    public PortfolioStock(Portfolio portfolio, Stock stock, Integer stockNum, BigDecimal unitPrice) {
        this.portfolio = portfolio;
        this.stock = stock;
        this.stockNum = stockNum;
        this.unitPrice = unitPrice;

        this.setRefField();
    }

    public void update(Portfolio portfolio, Stock stock, Integer stockNum, BigDecimal unitPrice) {
        this.portfolio = portfolio;
        this.stock = stock;
        this.stockNum = stockNum;
        this.unitPrice = unitPrice;
        this.setRefField();
    }

    private void setRefField() {

        if ( ObjectUtils.isNotEmpty(this.stockNum) && ObjectUtils.isNotEmpty(this.unitPrice) ) {
            this.totalTradingAmount = new BigDecimal(this.stockNum).multiply(this.unitPrice);
        }

        if ( ObjectUtils.isNotEmpty(this.stockNum) && ObjectUtils.isNotEmpty(this.stock.getCurrentPrice()) ) {
            this.evalAmount = new BigDecimal(this.stockNum).multiply(this.stock.getCurrentPrice());
        }

        if ( ObjectUtils.isNotEmpty(this.evalAmount) && ObjectUtils.isNotEmpty(this.totalTradingAmount) ) {
            this.earningAmount = this.evalAmount.subtract(this.totalTradingAmount);
        }

        if ( ObjectUtils.isNotEmpty(this.earningAmount) && ObjectUtils.isNotEmpty(this.totalTradingAmount) ) {
            this.earningRate = this.earningAmount.divide(totalTradingAmount, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        }
        if ( ObjectUtils.isNotEmpty(this.stockNum) && ObjectUtils.isNotEmpty(this.stock.getAnnualPayout()) ) {
            this.totalPayout = new BigDecimal(this.stockNum).multiply(this.stock.getAnnualPayout());
        }

        if ( ObjectUtils.isNotEmpty(this.totalPayout) && ObjectUtils.isNotEmpty(this.totalTradingAmount) ) {
            this.investmentDivYield = this.totalPayout.divide(this.totalTradingAmount, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        }
    }

    @Override
    public String toString() {
        return "PortfolioStock{" +
                "id=" + id +
                ", portfolio=" + portfolio.toString() +
                ", stock=" + stock.toString() +
                ", stockNum=" + stockNum +
                ", unitPrice=" + unitPrice +
                ", totalTradingAmount=" + totalTradingAmount +
                ", evalAmount=" + evalAmount +
                ", earningAmount=" + earningAmount +
                ", earningRate=" + earningRate +
                ", totalPayout=" + totalPayout +
                ", investmentDivYield=" + investmentDivYield +
                '}';
    }
}
