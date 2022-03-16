package com.nuritech.stock.mystock.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class InterestStockRepositoryTest {

    @Autowired
    StockRepository stockRepository;

    @Autowired

    InterestStockRepository interestStockRepository;

    @Test
    public void InterestStock저장_불러오기() {

        String ticker1 = "TEST1";
        String ticker2 = "TEST2";
        String ticker3 = "TEST3";

        Stock addStock1 = Stock.builder().ticker(ticker1).build();
        Stock addStock2 = Stock.builder().ticker(ticker2).build();
        Stock addStock3 = Stock.builder().ticker(ticker3).build();

        stockRepository.save(addStock1);
        stockRepository.save(addStock2);
        stockRepository.save(addStock3);

        List<Stock> stocks = stockRepository.findAll();
        for(Stock stock : stocks) {
            System.out.println(">>> interestStock stock id="+stock.getId()+" stock.ticker="+stock.getTicker());
        }

        Stock stockEntity1 = stockRepository.findByTicker(ticker1);
        Stock stockEntity2 = stockRepository.findByTicker(ticker2);
        Stock stockEntity3 = stockRepository.findByTicker(ticker3);

        InterestStock addInterestStock1 = InterestStock.builder()
                .email("mintaeho75@gmail.com")
                .stock(stockEntity1)
                .build();

        InterestStock addInterestStock2 = InterestStock.builder()
                .email("mintaeho75@gmail.com")
                .stock(stockEntity2)
                .build();

        InterestStock addInterestStock3 = InterestStock.builder()
                .email("mintaeho75@gmail.com")
                .stock(stockEntity3)
                .build();

        interestStockRepository.save(addInterestStock1);
        interestStockRepository.save(addInterestStock2);
        interestStockRepository.save(addInterestStock3);

        List<InterestStock> interestStocks = interestStockRepository.findAll();
        for(InterestStock interestStock : interestStocks) {
            System.out.println(">>> interestStock id="+interestStock.getId()+" stock.ticker="+interestStock.getStock().getTicker());
        }
        assertThat(interestStocks.size()).isEqualTo(3);
    }


    @Test
    @Transactional
    public void InterestStock수정_불러오기() {
        // 관심종목의 수정은 Stock의 수정과 동일하므로 현재 구조에서는 필요하지 않음
    }


    @Test
    @Transactional
    public void InterestStock삭제_불러오기() {
        this.InterestStock저장_불러오기();

        Long key = new Long(4);
        InterestStock removeInterestStock = interestStockRepository.findById(key)
                .orElseThrow(() -> new IllegalArgumentException("데이터가 없습니다."));

        interestStockRepository.delete(removeInterestStock);

        List<InterestStock> interestStocks = interestStockRepository.findAll();
        for(InterestStock interestStock : interestStocks) {
            System.out.println(">>> InterestStock삭제_불러오기 InterestStock id="+interestStock.getId()+" email="+interestStock.getEmail()+" stock.ticker="+interestStock.getStock().getTicker());
        }

        List<Stock> stocks = stockRepository.findAll();
        for(Stock stock : stocks) {
            System.out.println(">>> stock.getTicker() "+stock.getTicker());
        }

        assertThat(interestStocks.size()).isEqualTo(2);

    }

}