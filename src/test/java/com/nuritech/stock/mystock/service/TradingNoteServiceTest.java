package com.nuritech.stock.mystock.service;

import com.nuritech.stock.mystock.common.util.PropertiesUtils;
import com.nuritech.stock.mystock.domain.Portfolio;
import com.nuritech.stock.mystock.domain.PortfolioStock;
import com.nuritech.stock.mystock.domain.Stock;
import com.nuritech.stock.mystock.domain.TradingNote;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioSaveRequestDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockResponseDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockSaveRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockModifyRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import com.nuritech.stock.mystock.dto.tradingNote.TradingNoteModifyRequestDto;
import com.nuritech.stock.mystock.dto.tradingNote.TradingNoteResponseDto;
import com.nuritech.stock.mystock.dto.tradingNote.TradingNoteSaveRequestDto;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TradingNoteServiceTest {

    private final String DATA_EMAIL = "mintaeho75@gmail.com";
    private final String DATA_NAME = "첫번째포트폴리오";
    private final String DATA_NAME2 = "두번째포트폴리오";

    private final String DATA_TICKER = "AAPL";
    private final String DATA_STOCK_NAME = "Apple Inc.";
    private final String DATA_TICKER2 = "MSFT";
    private final String DATA_STOCK_NAME2 = "마이크로소프트";
    private final String DATA_TICKER3 = "NVDA";
    private final String DATA_STOCK_NAME3 = "엔비디아";
    private final String DATA_BUSINESS_CYCYLE = "";
    private final String DATA_SECTOR = "";
    private final BigDecimal DATA_CURRENT_PRICE = new BigDecimal(300);
    private final BigDecimal DATA_HIGHEST_PRICE = new BigDecimal(330.33);
    private final BigDecimal DATA_LOWER_PRICE = new BigDecimal(250.05);
    private final String DATA_NOBILITY_STOCK_YN = "";
    private final String DATA_DIVIDEND_PAY_MONTH = "";
    private final String DATA_COMPANY_INFO = "";
    private final BigDecimal DATA_ANNUAL_PAYOUT = null;
    private final BigDecimal DATA_DIVIDEND_YIELD = new BigDecimal(2.5);
    private final BigDecimal DATA_DIVIDEND_GROWTH_RATE = null;
    private final BigDecimal DATA_FIVEYEAR_GROWTH_RATE = null;
    private final BigDecimal DATA_PAYOUT_RATIO = null;
    private final BigDecimal DATA_FIVEYEAR_AVG_DIVIDEND_YITELD = null;
    private final String DATA_NATION = "US";

    @Autowired
    TradingNoteService service;

    @Autowired
    StockService service2;

    @Autowired
    PortfolioService service3;

    @Autowired
    PortfolioStockService service4;

    @Autowired
    PropertiesUtils propertiesUtils;

    @BeforeEach
    void setUp() {

        // Stock 등록 요청 객체 생성
        StockSaveRequestDto stock1 = StockSaveRequestDto.builder()
                .ticker(DATA_TICKER)
                .stockName(DATA_STOCK_NAME)
                .businessCycle(DATA_BUSINESS_CYCYLE)
                .sector(DATA_SECTOR)
                .currentPrice(DATA_CURRENT_PRICE)
                .highestPrice(DATA_HIGHEST_PRICE)
                .nation(DATA_NATION)
                .build();
        StockSaveRequestDto stock2 = StockSaveRequestDto.builder()
                .ticker(DATA_TICKER2)
                .stockName(DATA_STOCK_NAME2)
                .businessCycle(DATA_BUSINESS_CYCYLE)
                .sector(DATA_SECTOR)
                .currentPrice(DATA_CURRENT_PRICE)
                .highestPrice(DATA_HIGHEST_PRICE)
                .nation(DATA_NATION)
                .build();
        StockSaveRequestDto stock3 = StockSaveRequestDto.builder()
                .ticker(DATA_TICKER3)
                .stockName(DATA_STOCK_NAME3)
                .businessCycle(DATA_BUSINESS_CYCYLE)
                .sector(DATA_SECTOR)
                .currentPrice(DATA_CURRENT_PRICE)
                .highestPrice(DATA_HIGHEST_PRICE)
                .nation(DATA_NATION)
                .build();

        // 저장
        Long s_key1 = service2.save(stock1);
        Long s_key2 = service2.save(stock2);
        Long s_key3 = service2.save(stock3);

        // Portfolio 등록 객체 생성
        PortfolioSaveRequestDto portfolio1 = PortfolioSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .name(DATA_NAME)
                .build();
        PortfolioSaveRequestDto portfolio2 = PortfolioSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .name(DATA_NAME2)
                .build();

        // Portfolio 객체 저장
        Long p_key1 = service3.save(portfolio1);
        Long p_key2 = service3.save(portfolio2);

        Portfolio portfolioEntity1 = service3.findEntityById(p_key1);
        Portfolio portfolioEntity2 = service3.findEntityById(p_key2);
        Stock stockEntity1 = service2.findEntityById(s_key1);
        Stock stockEntity2 = service2.findEntityById(s_key2);
        Stock stockEntity3 = service2.findEntityById(s_key3);

        // PortfolioStock 등록 객체 생성 및 저장
        PortfolioStockSaveRequestDto psDto1 = PortfolioStockSaveRequestDto.builder()
                .portfolio(portfolioEntity1)
                .stock(stockEntity1)
                .stockNum(10)
                .unitPrice(new BigDecimal(100))
                .build();

        // 등록 요청객체2 생성
        PortfolioStockSaveRequestDto psDto2 = PortfolioStockSaveRequestDto.builder()
                .portfolio(portfolioEntity1)
                .stock(stockEntity2)
                .stockNum(20)
                .unitPrice(new BigDecimal(200))
                .build();

        // 등록 요청객체3 생성
        PortfolioStockSaveRequestDto psDto3 = PortfolioStockSaveRequestDto.builder()
                .portfolio(portfolioEntity2)
                .stock(stockEntity1)
                .stockNum(30)
                .unitPrice(new BigDecimal(300))
                .build();

        // 저장
        Long ps_key1 = service4.save(psDto1);
        Long ps_key2 = service4.save(psDto2);
        Long ps_key3 = service4.save(psDto3);
        System.out.println(">>> ps_key1="+ps_key1+", ps_key2="+ps_key2+", ps_key3="+ps_key3);

        List<PortfolioStockResponseDto> responseDtos = service4.findAll();
        for(PortfolioStockResponseDto dto : responseDtos) {
            System.out.println(">>> portfolioStock::id="+dto.getPortfolioStockId()+
                    ", portfolio name="+dto.getPortfolio().getName()+
                    ", ticker="+dto.getStock().getTicker()+
                    ", stockNum="+dto.getStockNum()
            );
        }

        PortfolioStock psEntity1 = service4.findEntityById(ps_key1);
        PortfolioStock psEntity2 = service4.findEntityById(ps_key2);

        // TradingNote 객체 생성
        TradingNoteSaveRequestDto requestDto1 = TradingNoteSaveRequestDto.builder()
                .email(DATA_EMAIL)
                //.portfolioStock(psEntity1)
                .portfolioId(ps_key1)
                .tradingDate("20220129")
                .tradingType("매수")
                .stockNum(10)
                .unitPrice(new BigDecimal(100))
                .tradingAmount(new BigDecimal(1000))
                .nation("US")
                .build();

        TradingNoteSaveRequestDto requestDto2 = TradingNoteSaveRequestDto.builder()
                .email(DATA_EMAIL)
                //.portfolioStock(psEntity2)
                .portfolioId(ps_key2)
                .tradingDate("20220129")
                .tradingType("매수")
                .stockNum(10)
                .unitPrice(new BigDecimal(100))
                .tradingAmount(new BigDecimal(1000))
                .nation("US")
                .build();

        service.save(requestDto1);
        service.save(requestDto2);

        printList("등록");
    }


    @Test
    @Transactional
    public void tradingNote_Save_List() {

        String ticker = "AAPL";
        String email = DATA_EMAIL;
        String name = DATA_NAME;

        // ticker로 Stock 객체 조회
        Stock stockEntity = service2.findEntityByTicker(ticker);

        // 포트폴리오명으로 Portfolio 객체 조회
        Portfolio portfolioEntity = service3.findEntityByEmailAndName(email, name);

        // PortfolioStock 객체 조회
        PortfolioStock portfolioStockEntity = service4.findEntityByStockAndPortfolio(stockEntity, portfolioEntity);

        if ( portfolioStockEntity != null ) {

            // TradingNote 객체 생성
            TradingNoteSaveRequestDto requestDto = TradingNoteSaveRequestDto.builder()
                    .email(email)
                    //.portfolioStock(portfolioStockEntity)
                    .portfolioId(portfolioEntity.getId())
                    .tradingDate("20220130")
                    .tradingType("매수")
                    .stockNum(10)
                    .unitPrice(new BigDecimal(100))
                    .tradingAmount(new BigDecimal(1000))
                    .nation("US")
                    .build();

            service.save(requestDto);
        }
        else {
            System.out.println("PortfolioStock is null...");
        }

        printList("등록");

        List<TradingNoteResponseDto> responseDtos = service.findByEmail(email);
        // 저장된 객체의 id가 1이면 통과
        assertThat(responseDtos.size()).isEqualTo(3);

    }

    @Test
    @Transactional
    public void tradingNote_Modify_List() {

        Long key = 9L;
        /*
        // id값으로 저장된 객체 조회
        TradingNote entity = service.findEntityById(key);

        // TradingNote의 child인 PortfolioStock의 Portfolio 수정
        String portfolioId = entity.getPortfolioId();

        String modifyName = "수정_첫번째포트폴리오";
        PortfolioModifyRequestDto modifyRequestDto = PortfolioModifyRequestDto.builder()
                .portfolioId(protfolioEntity.getId())
                .email(protfolioEntity.getEmail())
                .name(modifyName)
                .build();

        // 수정된 객체로 저장
        Long result2 = service3.modify(modifyRequestDto);

        // TradingNote 수정
        Integer modifyNum = 100;
        TradingNoteModifyRequestDto requestDto = TradingNoteModifyRequestDto.builder()
                .tradingNoteId(entity.getId())
                //.portfolioStock(entity.getPortfolioStock())
                .portfolioId(entity.getPortfolioStock().getPortfolio().getId())
                .ticker(entity.getPortfolioStock().getStock().getTicker())
                .stockNum(modifyNum)
                .build();

        Long result = service.modify(requestDto);
        System.out.println(">>> result="+result);
        //System.out.println(">>> result="+result+", result2="+result2+", result3="+result3);

        // 결과 출력
        printList("수정");

        List<TradingNoteResponseDto> responseDtos = service.findByEmail(DATA_EMAIL);

        // 첫번째 저장된 객체의 id가 9이면 통과
        assertThat(responseDtos.get(0).getTradingNoteId()).isEqualTo(9);

        // 첫번째 저장된 객체의 name이 수정 된 name과 동일하면 통과
        assertThat(responseDtos.get(0).getPortfolioStock().getPortfolio().getName()).isEqualTo(modifyName);

        // 첫번째 저장된 객체의 email이 수정 전 email과 동일하면 통과
        assertThat(responseDtos.get(0).getStockNum()).isEqualTo(modifyNum);
        */

    }

    @Test
    @Transactional
    public void tradingNote_Remove_List() {

        this.tradingNote_Save_List();

        Long key = 9L;

        // id값으로 저장된 객체 조회
        TradingNote entity = service.findEntityById(key);

        // 연관관계 삭제
        //entity.update(entity.getEmail(), null);

        // 객체 삭제
        service.remove(key);

        printList("삭제");
        List<TradingNoteResponseDto> responseDtos = service.findByEmail(DATA_EMAIL);

        // 사이즈가 0이면 통과
        assertThat(responseDtos.size()).isEqualTo(0);

    }

    private void printList(String mode) {

        System.out.println(">>>>>> 액션="+mode);
        /*
        // 저장 목록 조회 및 출력
        List<TradingNoteResponseDto> responseDtos = service.findByEmail(DATA_EMAIL);
        for(TradingNoteResponseDto dto : responseDtos) {
            System.out.println(">>> tradingNote::id="+dto.getTradingNoteId()+
                    ", trading date="+dto.getTradingDate()+
                    ", stock number="+dto.getStockNum()+
                    ", ticker="+dto.getPortfolioStock().getStock().getTicker()+
                    ", portfolio name="+dto.getPortfolioStock().getPortfolio().getName()
            );
        }

        // 저장 목록 조회 및 출력
        List<PortfolioStockResponseDto> responseDtos1 = service4.findAll();
        for(PortfolioStockResponseDto dto : responseDtos1) {
            System.out.println(">>> portfolioStock::portfolio name="+dto.getPortfolio().getName()+
                    ", ticker="+dto.getStock().getTicker()+
                    ", stockNum="+dto.getStockNum()
            );
        }

        // Portfolio 저장 목록 조회 및 출력
        List<PortfolioResponseDto> responseDto2 = service3.findByEmail(DATA_EMAIL);
        for(PortfolioResponseDto dto : responseDto2) {
            System.out.println(">>> portfolio name="+dto.getName()
            );
        }

        // Stock 저장 목록 조회 및 출력
        List<StockResponseDto> responseDtos3 = service2.findAll();
        for(StockResponseDto dto : responseDtos3) {
            System.out.println(">>> ticker="+dto.getTicker()+
                    ", stock name="+dto.getStockName()
            );
        }

         */

    }
}
