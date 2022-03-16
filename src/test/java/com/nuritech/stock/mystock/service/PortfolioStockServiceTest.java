package com.nuritech.stock.mystock.service;

import com.nuritech.stock.mystock.common.util.PropertiesUtils;
import com.nuritech.stock.mystock.domain.InterestStock;
import com.nuritech.stock.mystock.domain.Portfolio;
import com.nuritech.stock.mystock.domain.PortfolioStock;
import com.nuritech.stock.mystock.domain.Stock;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockResponseDto;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockSaveRequestDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioSaveRequestDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockResponseDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockSaveRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockModifyRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PortfolioStockServiceTest {

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
    private final BigDecimal DATA_CURRENT_PRICE1 = new BigDecimal(150);
    private final BigDecimal DATA_CURRENT_PRICE2 = new BigDecimal(250);
    private final BigDecimal DATA_CURRENT_PRICE3 = new BigDecimal(300);
    private final BigDecimal DATA_HIGHEST_PRICE = new BigDecimal(330.33);
    private final BigDecimal DATA_LOWER_PRICE = new BigDecimal(100.05);
    private final String DATA_NOBILITY_STOCK_YN = "";
    private final String DATA_DIVIDEND_PAY_MONTH = "";
    private final String DATA_COMPANY_INFO = "";
    private final BigDecimal DATA_ANNUAL_PAYOUT = null;
    private final BigDecimal DATA_DIVIDEND_YIELD = new BigDecimal(2.5);
    private final BigDecimal DATA_DIVIDEND_GROWTH = null;
    private final BigDecimal DATA_FIVEYEAR_GROWTH_RATE = null;
    private final BigDecimal DATA_PAYOUT_RATIO = null;
    private final BigDecimal DATA_FIVEYEAR_AVG_DIVIDEND_YITELD = null;
    private final String DATA_NATION = "US";


    @Autowired
    PortfolioStockService service;

    @Autowired
    PortfolioService service2;

    @Autowired
    StockService service3;

    @Autowired
    PropertiesUtils propertiesUtils;

    //@BeforeEach
    void setUp() {

        // Portfolio1 객체 생성
        PortfolioSaveRequestDto portfolioDto1 = PortfolioSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .name(DATA_NAME)
                .build();

        // Portfolio1 객체 저장
        Long p_key1 = service2.save(portfolioDto1);

        // Portfolio2 객체 생성
        PortfolioSaveRequestDto portfolioDto2 = PortfolioSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .name(DATA_NAME2)
                .build();

        // Portfolio1 객체 저장
        Long p_key2 = service2.save(portfolioDto2);

        // Stock1 객체 생성
        StockSaveRequestDto stockDto1 = StockSaveRequestDto.builder()
                .ticker(DATA_TICKER)
                .stockName(DATA_STOCK_NAME)
                .businessCycle(DATA_BUSINESS_CYCYLE)
                .sector(DATA_SECTOR)
                .currentPrice(DATA_CURRENT_PRICE1)
                .highestPrice(DATA_HIGHEST_PRICE)
                .lowerPrice(DATA_LOWER_PRICE)
                .nobilityStockYn(DATA_NOBILITY_STOCK_YN)
                .dividendPayMonth(DATA_DIVIDEND_PAY_MONTH)
                .companyInfo(DATA_COMPANY_INFO)
                .annualPayout(DATA_ANNUAL_PAYOUT)
                .dividendYield(DATA_DIVIDEND_YIELD)
                .dividendGrowth(DATA_DIVIDEND_GROWTH)
                .fiveyearGrowthRate(DATA_FIVEYEAR_GROWTH_RATE)
                .payoutRatio(DATA_PAYOUT_RATIO)
                .fiveyearAvgDividendYield(DATA_FIVEYEAR_AVG_DIVIDEND_YITELD)
                .nation(DATA_NATION)
                .build();

        // Stock1 객체 저장
        Long s_key1 = service3.save(stockDto1);

        // Stock2 객체 생성
        StockSaveRequestDto stockDto2 = StockSaveRequestDto.builder()
                .ticker(DATA_TICKER2)
                .stockName(DATA_STOCK_NAME2)
                .businessCycle(DATA_BUSINESS_CYCYLE)
                .sector(DATA_SECTOR)
                .currentPrice(DATA_CURRENT_PRICE2)
                .highestPrice(DATA_HIGHEST_PRICE)
                .lowerPrice(DATA_LOWER_PRICE)
                .nobilityStockYn(DATA_NOBILITY_STOCK_YN)
                .dividendPayMonth(DATA_DIVIDEND_PAY_MONTH)
                .companyInfo(DATA_COMPANY_INFO)
                .annualPayout(DATA_ANNUAL_PAYOUT)
                .dividendYield(DATA_DIVIDEND_YIELD)
                .dividendGrowth(DATA_DIVIDEND_GROWTH)
                .fiveyearGrowthRate(DATA_FIVEYEAR_GROWTH_RATE)
                .payoutRatio(DATA_PAYOUT_RATIO)
                .fiveyearAvgDividendYield(DATA_FIVEYEAR_AVG_DIVIDEND_YITELD)
                .nation(DATA_NATION)
                .build();

        // Stock2 객체 저장
        Long s_key2 = service3.save(stockDto2);

        // Stock2 객체 생성
        StockSaveRequestDto stockDto3 = StockSaveRequestDto.builder()
                .ticker(DATA_TICKER3)
                .stockName(DATA_STOCK_NAME3)
                .businessCycle(DATA_BUSINESS_CYCYLE)
                .sector(DATA_SECTOR)
                .currentPrice(DATA_CURRENT_PRICE3)
                .highestPrice(DATA_HIGHEST_PRICE)
                .lowerPrice(DATA_LOWER_PRICE)
                .nobilityStockYn(DATA_NOBILITY_STOCK_YN)
                .dividendPayMonth(DATA_DIVIDEND_PAY_MONTH)
                .companyInfo(DATA_COMPANY_INFO)
                .annualPayout(DATA_ANNUAL_PAYOUT)
                .dividendYield(DATA_DIVIDEND_YIELD)
                .dividendGrowth(DATA_DIVIDEND_GROWTH)
                .fiveyearGrowthRate(DATA_FIVEYEAR_GROWTH_RATE)
                .payoutRatio(DATA_PAYOUT_RATIO)
                .fiveyearAvgDividendYield(DATA_FIVEYEAR_AVG_DIVIDEND_YITELD)
                .nation(DATA_NATION)
                .build();

        // Stock2 객체 저장
        Long s_key3 = service3.save(stockDto3);

        Portfolio portfolioEntity1 = service2.findEntityById(p_key1);
        Portfolio portfolioEntity2 = service2.findEntityById(p_key2);
        Stock stockEntity1 = service3.findEntityById(s_key1);
        Stock stockEntity2 = service3.findEntityById(s_key2);
        Stock stockEntity3 = service3.findEntityById(s_key3);

        // 등록 요청객체1 생성
        PortfolioStockSaveRequestDto requestDto1 = PortfolioStockSaveRequestDto.builder()
                //.portfolio(portfolioEntity1)
                //.stock(stockEntity1)
                .portfolioId(p_key1)
                .ticker(DATA_TICKER)
                .stockNum(10)
                .unitPrice(new BigDecimal(100))
                .build();

        // 등록 요청객체2 생성
        PortfolioStockSaveRequestDto requestDto2 = PortfolioStockSaveRequestDto.builder()
                //.portfolio(portfolioEntity1)
                //.stock(stockEntity2)
                .portfolioId(p_key1)
                .ticker(DATA_TICKER2)
                .stockNum(20)
                .unitPrice(new BigDecimal(150))
                .build();

        // 등록 요청객체2 생성
        PortfolioStockSaveRequestDto requestDto3 = PortfolioStockSaveRequestDto.builder()
                //.portfolio(portfolioEntity1)
                //.stock(stockEntity2)
                .portfolioId(p_key1)
                .ticker(DATA_TICKER3)
                .stockNum(30)
                .unitPrice(new BigDecimal(180))
                .build();

        // 등록 요청객체3 생성
        PortfolioStockSaveRequestDto requestDto4 = PortfolioStockSaveRequestDto.builder()
                //.portfolio(portfolioEntity2)
                //.stock(stockEntity1)
                .portfolioId(p_key2)
                .ticker(DATA_TICKER)
                .stockNum(30)
                .unitPrice(new BigDecimal(300))
                .build();

        // 저장
        Long result1 = service.save(requestDto1);
        Long result2 = service.save(requestDto2);
        Long result3 = service.save(requestDto3);
        Long result4 = service.save(requestDto4);
        System.out.println(">>> result="+result1+", result2="+result2+", result3="+result3+", result4="+result4);

        // 저장 목록 조회 및 출력
        List<PortfolioStockResponseDto> responseDtos = service.findAll();
        for(PortfolioStockResponseDto dto : responseDtos) {
            System.out.println(">>> portfolioStock::id="+dto.getPortfolioStockId()+
                    ", portfolio name="+dto.getPortfolio().getName()+
                    ", ticker="+dto.getStock().getTicker()+
                    ", stockNum="+dto.getStockNum()
            );
        }

        // Portfolio 저장 목록 조회 및 출력
        List<PortfolioResponseDto> responseDto2 = service2.findByEmail(DATA_EMAIL);
        for(PortfolioResponseDto dto : responseDto2) {
            System.out.println(">>> portfolio id="+dto.getPortfolioId()+", name="+dto.getName());
        }

        // Stock 저장 목록 조회 및 출력
        List<StockResponseDto> responseDtos3 = service3.findAll();
        for(StockResponseDto dto : responseDtos3) {
            System.out.println(">>> stock id="+dto.getStockId()+", ticker="+dto.getTicker()+
                    ", stock name="+dto.getStockName()
            );
        }
    }

    @Test
    @Transactional
    public void portfolioStock_Save_List() {

        // Portfolio1 객체 생성
        PortfolioSaveRequestDto portfolioDto1 = PortfolioSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .name(DATA_NAME)
                .build();

        // Portfolio1 객체 저장
        Long p_key1 = service2.save(portfolioDto1);

        // Portfolio2 객체 생성
        PortfolioSaveRequestDto portfolioDto2 = PortfolioSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .name(DATA_NAME2)
                .build();

        // Portfolio1 객체 저장
        Long p_key2 = service2.save(portfolioDto2);

        // Stock1 객체 생성
        StockSaveRequestDto stockDto1 = StockSaveRequestDto.builder()
                .ticker(DATA_TICKER)
                .stockName(DATA_STOCK_NAME)
                .businessCycle(DATA_BUSINESS_CYCYLE)
                .sector(DATA_SECTOR)
                .currentPrice(DATA_CURRENT_PRICE1)
                .highestPrice(DATA_HIGHEST_PRICE)
                .lowerPrice(DATA_LOWER_PRICE)
                .nobilityStockYn(DATA_NOBILITY_STOCK_YN)
                .dividendPayMonth(DATA_DIVIDEND_PAY_MONTH)
                .companyInfo(DATA_COMPANY_INFO)
                .annualPayout(DATA_ANNUAL_PAYOUT)
                .dividendYield(DATA_DIVIDEND_YIELD)
                .dividendGrowth(DATA_DIVIDEND_GROWTH)
                .fiveyearGrowthRate(DATA_FIVEYEAR_GROWTH_RATE)
                .payoutRatio(DATA_PAYOUT_RATIO)
                .fiveyearAvgDividendYield(DATA_FIVEYEAR_AVG_DIVIDEND_YITELD)
                .nation(DATA_NATION)
                .build();

        // Stock1 객체 저장
        Long s_key1 = service3.save(stockDto1);

        // Stock2 객체 생성
        StockSaveRequestDto stockDto2 = StockSaveRequestDto.builder()
                .ticker(DATA_TICKER2)
                .stockName(DATA_STOCK_NAME2)
                .businessCycle(DATA_BUSINESS_CYCYLE)
                .sector(DATA_SECTOR)
                .currentPrice(DATA_CURRENT_PRICE2)
                .highestPrice(DATA_HIGHEST_PRICE)
                .lowerPrice(DATA_LOWER_PRICE)
                .nobilityStockYn(DATA_NOBILITY_STOCK_YN)
                .dividendPayMonth(DATA_DIVIDEND_PAY_MONTH)
                .companyInfo(DATA_COMPANY_INFO)
                .annualPayout(DATA_ANNUAL_PAYOUT)
                .dividendYield(DATA_DIVIDEND_YIELD)
                .dividendGrowth(DATA_DIVIDEND_GROWTH)
                .fiveyearGrowthRate(DATA_FIVEYEAR_GROWTH_RATE)
                .payoutRatio(DATA_PAYOUT_RATIO)
                .fiveyearAvgDividendYield(DATA_FIVEYEAR_AVG_DIVIDEND_YITELD)
                .nation(DATA_NATION)
                .build();

        // Stock2 객체 저장
        Long s_key2 = service3.save(stockDto2);

        Portfolio portfolioEntity1 = service2.findEntityById(p_key1);
        Portfolio portfolioEntity2 = service2.findEntityById(p_key2);
        Stock stockEntity1 = service3.findEntityById(s_key1);
        Stock stockEntity2 = service3.findEntityById(s_key2);

        // 등록 요청객체1 생성
        PortfolioStockSaveRequestDto requestDto1 = PortfolioStockSaveRequestDto.builder()
                .portfolio(portfolioEntity1)
                .stock(stockEntity1)
                .stockNum(10)
                .unitPrice(new BigDecimal(100))
                .build();

        // 등록 요청객체2 생성
        PortfolioStockSaveRequestDto requestDto2 = PortfolioStockSaveRequestDto.builder()
                .portfolio(portfolioEntity1)
                .stock(stockEntity2)
                .stockNum(20)
                .unitPrice(new BigDecimal(200))
                .build();

        // 등록 요청객체3 생성
        PortfolioStockSaveRequestDto requestDto3 = PortfolioStockSaveRequestDto.builder()
                .portfolio(portfolioEntity2)
                .stock(stockEntity1)
                .stockNum(30)
                .unitPrice(new BigDecimal(300))
                .build();

        // 저장
        Long result1 = service.save(requestDto1);
        Long result2 = service.save(requestDto2);
        Long result3 = service.save(requestDto3);
        System.out.println(">>> result="+result1+", result2="+result2+", result3="+result3);

        // 저장 목록 조회 및 출력
        List<PortfolioStockResponseDto> responseDtos = service.findAll();
        for(PortfolioStockResponseDto dto : responseDtos) {
            System.out.println(">>> portfolioStock::id="+dto.getPortfolioStockId()+
                    ", portfolio name="+dto.getPortfolio().getName()+
                    ", ticker="+dto.getStock().getTicker()+
                    ", stockNum="+dto.getStockNum()
            );
        }

        // Portfolio 저장 목록 조회 및 출력
        List<PortfolioResponseDto> responseDto2 = service2.findByEmail(DATA_EMAIL);
        for(PortfolioResponseDto dto : responseDto2) {
            System.out.println(">>> portfolio name="+dto.getName()
            );
        }

        // Stock 저장 목록 조회 및 출력
        List<StockResponseDto> responseDtos3 = service3.findAll();
        for(StockResponseDto dto : responseDtos3) {
            System.out.println(">>> ticker="+dto.getTicker()+
                    ", stock name="+dto.getStockName()
            );
        }

        // 저장된 객체의 id가 1이면 통과
        assertThat(responseDtos.size()).isEqualTo(3);
    }


    @Test
    @Transactional
    public void portfolioStock_Modify_List() {

        this.portfolioStock_Save_List();

        Long key = 5L;

        // id값으로 저장된 객체 조회
        PortfolioStock entity = service.findEntityById(key);

        Integer modifyNum = 100;
        PortfolioStockModifyRequestDto requestDto = PortfolioStockModifyRequestDto.builder()
                .portfolioStockId(entity.getId())
                .portfolio(entity.getPortfolio())
                .stock(entity.getStock())
                .stockNum(modifyNum)
                .build();

        Long result = service.modify(requestDto);
        System.out.println(">>> result="+result);
        //System.out.println(">>> result="+result+", result2="+result2+", result3="+result3);

        // Stock도 수정
        String modifyTicker = "UPD_AAPL";
        StockModifyRequestDto stockModifyRequestDto = StockModifyRequestDto.builder()
                .stockId(entity.getStock().getId())
                .ticker(modifyTicker)
                .build();
        PropertiesUtils.copyNonNullProperties(entity.getStock(), stockModifyRequestDto);
        service3.modify(stockModifyRequestDto);

        // 저장 목록 조회 및 출력
        List<PortfolioStockResponseDto> responseDtos = service.findAll();
        for(PortfolioStockResponseDto dto : responseDtos) {
            System.out.println(">>> portfolioStock::portfolio name="+dto.getPortfolio().getName()+
                    ", ticker="+dto.getStock().getTicker()+
                    ", stockNum="+dto.getStockNum()
            );
        }

        // Portfolio 저장 목록 조회 및 출력
        List<PortfolioResponseDto> responseDto2 = service2.findByEmail(DATA_EMAIL);
        for(PortfolioResponseDto dto : responseDto2) {
            System.out.println(">>> portfolio name="+dto.getName()
            );
        }

        // Stock 저장 목록 조회 및 출력
        List<StockResponseDto> responseDtos3 = service3.findAll();
        for(StockResponseDto dto : responseDtos3) {
            System.out.println(">>> ticker="+dto.getTicker()+
                    ", stock name="+dto.getStockName()
            );
        }

        // 첫번째 저장된 객체의 id가 5이면 통과
        assertThat(responseDtos.get(0).getPortfolioStockId()).isEqualTo(5);

        // 첫번째 저장된 객체의 name이 수정 된 name과 동일하면 통과
        assertThat(responseDtos.get(0).getStock().getTicker()).isEqualTo(modifyTicker);

        // 첫번째 저장된 객체의 email이 수정 전 email과 동일하면 통과
        assertThat(responseDtos.get(0).getPortfolio().getEmail()).isEqualTo(DATA_EMAIL);


    }

    @Test
    @Transactional
    public void portfolioStock_Remove_List() {

        this.portfolioStock_Save_List();

        Long key = 5L;

        // id값으로 저장된 객체 조회
        PortfolioStock entity = service.findEntityById(key);

        // 연관관계 삭제
        //entity.update(entity.getEmail(), null);

        // 객체 삭제
        //service.remove(key);
        Long portfolioId = 1L;
        String ticker = DATA_TICKER2;
        //service.remove(portfolioId, ticker);

        // 저장 목록 조회 및 출력
        List<PortfolioStockResponseDto> responseDtos = service.findAll();
        for(PortfolioStockResponseDto dto : responseDtos) {
            System.out.println(">>> portfolioStock::portfolio name="+dto.getPortfolio().getName()+
                    ", ticker="+dto.getStock().getTicker()+
                    ", stockNum="+dto.getStockNum()
            );
        }

        // Portfolio 저장 목록 조회 및 출력
        List<PortfolioResponseDto> responseDto2 = service2.findByEmail(DATA_EMAIL);
        for(PortfolioResponseDto dto : responseDto2) {
            System.out.println(">>> portfolio name="+dto.getName()
            );
        }

        // Stock 저장 목록 조회 및 출력
        List<StockResponseDto> responseDtos3 = service3.findAll();
        for(StockResponseDto dto : responseDtos3) {
            System.out.println(">>> ticker="+dto.getTicker()+
                    ", stock name="+dto.getStockName()
            );
        }

        // 첫번째 저장된 객체의 id가 6이면 통과
        assertThat(responseDtos.get(0).getPortfolioStockId()).isEqualTo(6);

        // 첫번째 저장된 객체의 name이 MSFT와 동일하면 통과
        assertThat(responseDtos.get(0).getStock().getTicker()).isEqualTo(DATA_TICKER2);

        // 첫번째 저장된 객체의 email이 수정 전 email과 동일하면 통과
        assertThat(responseDtos.get(0).getPortfolio().getEmail()).isEqualTo(DATA_EMAIL);
    }

    @Test
    @Transactional
    public void findStockByPortfolioId_List() {

        String email = DATA_EMAIL;
        //Long portfolioId = 1L;
        Long portfolioId = 474L;

        // id값으로 저장된 객체 조회
        List<PortfolioStockResponseDto> entities = service.findStockByPortfolioId(email, portfolioId, Sort.Direction.ASC, "totalTradingAmount");

        for(PortfolioStockResponseDto dto : entities) {
            System.out.println(">>> list portfolioStock::portfolio name="+dto.getPortfolio().getName()+
                    ", ticker="+dto.getStock().getTicker()+
                    ", stockNum="+dto.getStockNum()+
                    ", earningRate="+dto.getEarningRate()+
                    ", totalTradingAmount="+dto.getTotalTradingAmount()
            );
        }

    }



}
