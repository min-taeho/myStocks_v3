package com.nuritech.stock.mystock.domain;

import com.nuritech.stock.mystock.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="INTEREST_STOCK")
@Getter
@NoArgsConstructor
public class InterestStock extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "STOCK_ID")
    private Stock stock;

    @Builder
    public InterestStock(String email, Stock stock) {
        this.email = email;
        this.stock = stock;
    }

    public void update(String email, Stock stock) {
        this.email = email;
        this.stock = stock;
    }
}
