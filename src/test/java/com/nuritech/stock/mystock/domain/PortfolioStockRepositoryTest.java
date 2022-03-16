package com.nuritech.stock.mystock.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PortfolioStockRepositoryTest {

    @Autowired
    StockRepository stockRepository;
    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    PortfolioStockRepository portfolioStockRepository;

    @Test
    public void PortfolioStock저장_불러오기() {

        // Stock 저장
        Stock addStock1 = Stock.builder().ticker("TEST1").build();
        Stock addStock2 = Stock.builder().ticker("TEST2").build();
        Stock addStock3 = Stock.builder().ticker("TEST3").build();
        stockRepository.save(addStock1);
        stockRepository.save(addStock2);
        stockRepository.save(addStock3);

        // Portfolio 저장
        Portfolio addPortfolio1 = Portfolio.builder()
                .email("mintaeho75@gmail.com")
                .name("테스트 포트폴리오1")
                .build();
        Portfolio addPortfolio2 = Portfolio.builder()
                .email("mintaeho75@gmail.com")
                .name("테스트 포트폴리오2")
                .build();
        Portfolio addPortfolio3 = Portfolio.builder()
                .email("mintaeho75@gmail.com")
                .name("테스트 포트폴리오3")
                .build();

        portfolioRepository.save(addPortfolio1);
        portfolioRepository.save(addPortfolio2);
        portfolioRepository.save(addPortfolio3);

        // PortfolioStock 저장
        PortfolioStock addPortfolioStock1A = PortfolioStock.builder()
                .portfolio(addPortfolio1)
                .stock(addStock1)
                .stockNum(10)
                .unitPrice(new BigDecimal(100))
                .build();

        PortfolioStock addPortfolioStock1B = PortfolioStock.builder()
                .portfolio(addPortfolio1)
                .stock(addStock2)
                .stockNum(5)
                .unitPrice(new BigDecimal(50))
                .build();

        portfolioStockRepository.save(addPortfolioStock1A);
        portfolioStockRepository.save(addPortfolioStock1B);

        PortfolioStock addPortfolioStock2 = PortfolioStock.builder()
                .portfolio(addPortfolio2)
                .stock(addStock2)
                .stockNum(20)
                .unitPrice(new BigDecimal(200))
                .build();
        portfolioStockRepository.save(addPortfolioStock2);

        PortfolioStock addPortfolioStock3A = PortfolioStock.builder()
                .portfolio(addPortfolio3)
                .stock(addStock2)
                .stockNum(30)
                .unitPrice(new BigDecimal(300))
                .build();

        PortfolioStock addPortfolioStock3B = PortfolioStock.builder()
                .portfolio(addPortfolio3)
                .stock(addStock3)
                .stockNum(35)
                .unitPrice(new BigDecimal(30))
                .build();

        portfolioStockRepository.save(addPortfolioStock3A);
        portfolioStockRepository.save(addPortfolioStock3B);

        List<PortfolioStock> list = portfolioStockRepository.findAll();
        for(PortfolioStock obj : list) {
            System.out.println(">>> portfolioStock id="+obj.getId()+
                    " portfolio name="+obj.getPortfolio().getName()+
                    " stock ticker="+obj.getStock().getTicker()+
                    " stock number="+obj.getStockNum()
            );
        }
        assertThat(list.size()).isEqualTo(5);


    }

}