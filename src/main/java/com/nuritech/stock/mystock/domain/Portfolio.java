package com.nuritech.stock.mystock.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nuritech.stock.mystock.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
@Table(name="PORTFOLIO")
public class Portfolio extends BaseTimeEntity {

    @Id
    @Column(name = "PORTFOLIO_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 역방향 객체 그래프 탐색
    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioStock> portfolioStocks;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Builder
    public Portfolio(List<PortfolioStock> portfolioStocks, String email, String name) {
        this.portfolioStocks = portfolioStocks;
        this.email = email;
        this.name = name;
    }

    public void update(List<PortfolioStock> portfolioStocks, String email, String name) {
        this.portfolioStocks = portfolioStocks;
        this.email = email;
        this.name = name;
    }

}
