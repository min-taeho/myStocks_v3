package com.nuritech.stock.mystock.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TradingNoteRepositoryTest {

    @Autowired
    StockRepository stockRepository;
    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    PortfolioStockRepository portfolioStockRepository;

    @Autowired
    TradingNoteRepository tradingNoteRepository;

    @BeforeEach
    void setUp() {

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

        PortfolioStock addPortfolioStock2A = PortfolioStock.builder()
                .portfolio(addPortfolio2)
                .stock(addStock1)
                .stockNum(10)
                .unitPrice(new BigDecimal(100))
                .build();


        portfolioStockRepository.save(addPortfolioStock1A);
        portfolioStockRepository.save(addPortfolioStock1B);
        portfolioStockRepository.save(addPortfolioStock2A);

    }

    @Test
    public void TradingNote저장_불러오기() {

        // to-do
        // 이메일, ticker, 포트폴리오명으로 객체 검색
        // 검색된 객체로 tradingnote 입력
        String email = "mintaeho75@gmail.com";
        String portfolioName1 = "테스트 포트폴리오1";
        String portfolioName2 = "테스트 포트폴리오2";
        String ticker1 = "TEST1";
        String ticker2 = "TEST2";
        String tradingDate = "20220127";
        String tradingType = "매수";    // 매수 또는 매도

        Stock stock1 = stockRepository.findByTicker(ticker1);
        Stock stock2 = stockRepository.findByTicker(ticker2);
        Portfolio portfolio1 = portfolioRepository.findByEmailAndName(email, portfolioName1);
        Portfolio portfolio2 = portfolioRepository.findByEmailAndName(email, portfolioName2);
        PortfolioStock portfolioStock1 = portfolioStockRepository.findByStockAndPortfolio(stock1, portfolio1);
        PortfolioStock portfolioStock2 = portfolioStockRepository.findByStockAndPortfolio(stock2, portfolio1);
        PortfolioStock portfolioStock3 = portfolioStockRepository.findByStockAndPortfolio(stock1, portfolio2);

        if ( portfolioStock1 != null ) {
            TradingNote tradingNote1 = TradingNote.builder()
                    .email(email)
                    .stock(stock1)
                    .portfolioId(portfolio1.getId())
                    .tradingType(tradingType)
                    .tradingDate(tradingDate)
                    .stockNum(10)
                    .unitPrice(new BigDecimal(1000))
                    .tradingAmount(new BigDecimal(10000))
                    .build();
            tradingNoteRepository.save(tradingNote1);
        }

        if ( portfolioStock2 != null ) {
            TradingNote tradingNote2 = TradingNote.builder()
                    .email(email)
                    .stock(stock2)
                    .portfolioId(portfolio2.getId())
                    .tradingType(tradingType)
                    .tradingDate(tradingDate)
                    .stockNum(20)
                    .unitPrice(new BigDecimal(2000))
                    .tradingAmount(new BigDecimal(40000))
                    .build();
            tradingNoteRepository.save(tradingNote2);
        }

        if ( portfolioStock3 != null ) {
            TradingNote tradingNote3 = TradingNote.builder()
                    .email(email)
                    .stock(stock1)
                    .portfolioId(portfolio2.getId())
                    .tradingType(tradingType)
                    .tradingDate(tradingDate)
                    .stockNum(30)
                    .unitPrice(new BigDecimal(3000))
                    .tradingAmount(new BigDecimal(90000))
                    .build();
            tradingNoteRepository.save(tradingNote3);
        }

        List<TradingNote> list = tradingNoteRepository.findByEmailOrderByTradingDateDesc(email);
        this.printList(list, "등록");

        assertThat(list.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void TradingNote수정_불러오기() {

        this.TradingNote저장_불러오기();

        String email = "mintaeho75@gmail.com";
        String portfolioName1 = "테스트 포트폴리오1";
        String ticker1 = "TEST1";
        String tradingDate = "20220127";
        String tradingType = "매수";    // 매수 또는 매도

        Stock stock1 = stockRepository.findByTicker(ticker1);
        Portfolio portfolio1 = portfolioRepository.findByEmailAndName(email, portfolioName1);
        PortfolioStock portfolioStock1 = portfolioStockRepository.findByStockAndPortfolio(stock1, portfolio1);

        TradingNote modifyTradingNote = tradingNoteRepository.findByStockAndPortfolioIdAndTradingDateAndTradingType(stock1, portfolio1.getId(), tradingDate, tradingType);

        if ( modifyTradingNote != null ) {
            modifyTradingNote.update(email,
                    stock1,
                    portfolio1.getId(),
                    "20220101",
                    "매도",
                    50,
                    new BigDecimal(500),
                    new BigDecimal(25000),
                    null,
                    null,
                    "US"
                    );
        }

        List<TradingNote> list = tradingNoteRepository.findByEmailOrderByTradingDateDesc(email);
        this.printList(list, "수정");

        assertThat(list.size()).isEqualTo(3);

    }

    @Test
    @Transactional
    public void TradingNote삭제_불러오기() {

        this.TradingNote저장_불러오기();

        String email = "mintaeho75@gmail.com";
        String portfolioName1 = "테스트 포트폴리오1";
        String ticker1 = "TEST1";
        String tradingDate = "20220127";
        String tradingType = "매수";    // 매수 또는 매도

        Stock stock1 = stockRepository.findByTicker(ticker1);
        Portfolio portfolio1 = portfolioRepository.findByEmailAndName(email, portfolioName1);
        PortfolioStock portfolioStock1 = portfolioStockRepository.findByStockAndPortfolio(stock1, portfolio1);

        TradingNote target = tradingNoteRepository.findByStockAndPortfolioIdAndTradingDateAndTradingType(stock1, portfolio1.getId(), tradingDate, tradingType);

        tradingNoteRepository.delete(target);

        List<TradingNote> list = tradingNoteRepository.findAll();
        this.printList(list, "삭제");

        assertThat(list.size()).isEqualTo(2);

    }

    private void printList(List<TradingNote> tradingNotes, String action) {

        for(TradingNote tradingNote : tradingNotes) {
            System.out.println(">>> TradingNote"+action+"_불러오기 tradingNote id="+tradingNote.getId()+
                    " portfolio id="+tradingNote.getPortfolioId()+
                    " tradingNote.unitPrice="+tradingNote.getUnitPrice()+
                    " tradingNote.ticker="+tradingNote.getStock().getTicker()
            );
        }
    }
}