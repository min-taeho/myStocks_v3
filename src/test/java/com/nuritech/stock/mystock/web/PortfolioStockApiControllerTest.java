package com.nuritech.stock.mystock.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuritech.stock.mystock.domain.Portfolio;
import com.nuritech.stock.mystock.domain.Stock;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioSaveRequestDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockResponseDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockSaveRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import com.nuritech.stock.mystock.service.PortfolioService;
import com.nuritech.stock.mystock.service.PortfolioStockService;
import com.nuritech.stock.mystock.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PortfolioStockApiControllerTest {

    @Autowired
    PortfolioStockApiController portfolioStockApiController;

    @Autowired
    private ObjectMapper mapper; // 객체를 json 형식으로 변경 시 사용

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String _BASE_URL = "http://localhost:";

    @Autowired
    PortfolioStockService service;

    @Autowired
    PortfolioService service2;

    @Autowired
    StockService service3;

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

    @BeforeEach
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
    public void save_test() throws Exception {

        String _mappingPath  = "/api/v1/portfolioStock";
        String name3 = "테스트포트폴리오3";

        PortfolioStockSaveRequestDto requestDto = PortfolioStockSaveRequestDto.builder()
                .portfolioId(1L)
                .ticker("ABBV")
                //.stockNum(30)
                //.unitPrice(new BigDecimal(300))
                .build();

        String url = _BASE_URL + port + _mappingPath;

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        // 결과 조회
        // 저장 목록 조회 및 출력
        List<PortfolioStockResponseDto> responseDtos = service.findAll();
        for(PortfolioStockResponseDto dto : responseDtos) {
            System.out.println(">>> portfolioStock::id="+dto.getPortfolioStockId()+
                    ", portfolio name="+dto.getPortfolio().getName()+
                    ", ticker="+dto.getStock().getTicker()+
                    ", stockNum="+dto.getStockNum()
            );
        }

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

    }

    /**
     * TestRestTemplate 을 이용한 Controller 테스트
     * @throws Exception
     */
    @Test
    public void findPortfolioStockByPortfolioId_test() throws Exception {

        String _mappingPath  = "/api/v1/portfolioStock/byEmailAndPortfolioId?email=mintaeho75@gmail.com&portfolioId=1";
        String url = _BASE_URL + port + _mappingPath;

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        //body.add("email", "mintaeho75@gmail.com");
        body.add("portfolioId", "1");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> httpEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        System.out.println(">>> responseEntity="+responseEntity.getBody());

    }

    @Test
    public void findPortfolioStockByPortfolioId_test2() throws Exception {

        String email = DATA_EMAIL;
        Long portfolioId = 1L;

        List<PortfolioStockResponseDto> dtos = portfolioStockApiController.findStockByPortfolioId(email, portfolioId, "earningRate", "Desc");
        for(PortfolioStockResponseDto dto : dtos) {
            System.out.println(">>> test2::portfolioStock::id="+dto.getPortfolioStockId()+
                    ", portfolio id="+dto.getPortfolio().getPortfolioId()+
                    ", portfolio name="+dto.getPortfolio().getName()+
                    ", ticker="+dto.getStock().getTicker()+
                    ", stockNum="+dto.getStockNum()
            );
        }



    }


    @Test
    public void portfolioStock_remove_get() throws Exception {

        Long portfolioId = 1L;
        String ticker = DATA_TICKER2;
        //portfolioStockApiController.remove(portfolioId, ticker, 1L);

        // 결과 조회
        // 저장 목록 조회 및 출력
        List<PortfolioStockResponseDto> responseDtos = service.findAll();
        for(PortfolioStockResponseDto dto : responseDtos) {
            System.out.println(">>> portfolioStock::id="+dto.getPortfolioStockId()+
                    ", portfolio name="+dto.getPortfolio().getName()+
                    ", ticker="+dto.getStock().getTicker()+
                    ", stockNum="+dto.getStockNum()
            );
        }

        assertThat(responseDtos.size()).isEqualTo(2);


    }


}
