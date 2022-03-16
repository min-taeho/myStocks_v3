package com.nuritech.stock.mystock.service;

import com.nuritech.stock.mystock.domain.Portfolio;
import com.nuritech.stock.mystock.domain.Stock;
import com.nuritech.stock.mystock.domain.StockRepository;
import com.nuritech.stock.mystock.dto.stock.StockModifyRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import lombok.extern.slf4j.Slf4j;
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
public class StockServiceTest {

    private final String DATA_TICKER = "AAPL";
    private final String DATA_STOCK_NAME = "Apple Inc.";
    private final String DATA_TICKER2 = "MSFT";
    private final String DATA_STOCK_NAME2 = "마이크로소프트";
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
    private final BigDecimal DATA_DIVIDEND_GROWTH = null;
    private final BigDecimal DATA_FIVEYEAR_GROWTH_RATE = null;
    private final BigDecimal DATA_PAYOUT_RATIO = null;
    private final BigDecimal DATA_FIVEYEAR_AVG_DIVIDEND_YITELD = null;
    private final String DATA_NATION = "";

    @Autowired
    StockService service;

    @Test
    @Transactional
    public void sotck_Save_List() {

        // 등록 요청 객체 생성
        StockSaveRequestDto requestDto = StockSaveRequestDto.builder()
                .ticker(DATA_TICKER)
                .stockName(DATA_STOCK_NAME)
                .businessCycle(DATA_BUSINESS_CYCYLE)
                .sector(DATA_SECTOR)
                .currentPrice(DATA_CURRENT_PRICE)
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

        // 저장
        Long result = service.save(requestDto);
        System.out.println(">>> result="+result);

        // 저장목록 조회 및 출력
        List<StockResponseDto> responseDtos = service.findAll();
        for(StockResponseDto dto : responseDtos) {
            System.out.println(">>> stockResponseDto.getTicker()="+dto.getTicker()+
                    ", stockResponseDto.getStockName()="+dto.getStockName());
        }

        // 저장된 Stock 목록의 갯수가 1이면 통과
        assertThat(result).isEqualTo(1);
    }


    @Test
    @Transactional
    public void sotck_Modify_List() {

        // 저장 요청 객체 생성
        StockSaveRequestDto requestDto = StockSaveRequestDto.builder()
                .ticker(DATA_TICKER)
                .stockName(DATA_STOCK_NAME)
                .businessCycle(DATA_BUSINESS_CYCYLE)
                .sector(DATA_SECTOR)
                .currentPrice(DATA_CURRENT_PRICE)
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

        // 객체 저장
        Long result = service.save(requestDto);
        System.out.println(">>> result="+result);

        // 저장목록 조회 및 출력
        List<StockResponseDto> responseDtos = service.findAll();
        for(StockResponseDto dto : responseDtos) {
            System.out.println(">>> stockResponseDto.getTicker()="+dto.getTicker()+
                    ", stockResponseDto.getStockName()="+dto.getStockName());
        }

        // key로 Stock 객체 조회
        StockResponseDto stockResponseDto1 = service.findById(result);

        // Stock 수정 요청 객체 생성 및 수정대상 항목에 수정된 값 반영
        // stock name 값 변경
        String modifyStockName = "Iphone Inc.";
        StockModifyRequestDto stockModifyRequestDto = StockModifyRequestDto.builder()
                .stockId(result)
                .ticker(stockResponseDto1.getTicker())
                .stockName(modifyStockName)
                .build();

        // 수정된 객체로 저장
        Long result2 = service.modify(stockModifyRequestDto);
        System.out.println(">>> result2="+result2);

        // 저장목록 조회 및 출력
        responseDtos = service.findAll();
        for(StockResponseDto dto : responseDtos) {
            System.out.println(">>> stockResponseDto.getTicker()="+dto.getTicker()+
                    ", stockResponseDto.getStockName()="+dto.getStockName());
        }

        // 조회된 객체의 id 값이 1이면 통과
        assertThat(result).isEqualTo(1);

        // 조회된 객체의 stock name이 변경된 stock name이면 통과
        assertThat(responseDtos.get(0).getStockName()).isEqualTo(modifyStockName);

        // 조회된 객체의 ticker가 변경전 ticker와 동일하면 통과
        assertThat(responseDtos.get(0).getTicker()).isEqualTo(DATA_TICKER);
    }

    @Test
    @Transactional
    public void sotck_Remove_List() {

        // Stock 저장 요청 객체 2개 생성
        StockSaveRequestDto requestDto = StockSaveRequestDto.builder()
                .ticker(DATA_TICKER)
                .stockName(DATA_STOCK_NAME)
                .businessCycle(DATA_BUSINESS_CYCYLE)
                .sector(DATA_SECTOR)
                .currentPrice(DATA_CURRENT_PRICE)
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

        StockSaveRequestDto requestDto2 = StockSaveRequestDto.builder()
                .ticker(DATA_TICKER2)
                .stockName(DATA_STOCK_NAME2)
                .businessCycle(DATA_BUSINESS_CYCYLE)
                .sector(DATA_SECTOR)
                .currentPrice(DATA_CURRENT_PRICE)
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

        // Stock 객체 2개 저장
        Long result = service.save(requestDto);
        System.out.println(">>> result="+result);

        Long result2 = service.save(requestDto2);
        System.out.println(">>> result2="+result2);

        // 저장된 Stock 객체 목록 조회
        List<StockResponseDto> responseDtos = service.findAll();
        for(StockResponseDto dto : responseDtos) {
            System.out.println(">>> 삭제전::stockResponseDto.getTicker()="+dto.getTicker()+
                    ", stockResponseDto.getStockName()="+dto.getStockName());
        }

        // 저장된 Soock객체중 1번 객체 삭제
        // remove
        service.remove(result);

        // 저장된 Stock 객체 목록 조회
        responseDtos = service.findAll();
        for(StockResponseDto dto : responseDtos) {
            System.out.println(">>> 삭제후::stockResponseDto.getTicker()="+dto.getTicker()+
                    ", stockResponseDto.getStockName()="+dto.getStockName());
        }

        // 저장된 객체가 1개이면 통과
        assertThat(responseDtos.size()).isEqualTo(1);

        // 저장된 객체의 ticker가 2번 ticker와 동일하면 통과
        assertThat(responseDtos.get(0).getTicker()).isEqualTo(DATA_TICKER2);
    }
}
