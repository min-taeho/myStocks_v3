package com.nuritech.stock.mystock.service;

import com.nuritech.stock.mystock.common.util.PropertiesUtils;
import com.nuritech.stock.mystock.domain.InterestStock;
import com.nuritech.stock.mystock.domain.Stock;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockResponseDto;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockSaveRequestDto;
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
public class InterestStockServiceTest {

    private final String DATA_EMAIL = "mintaeho75@gmail.com";

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
    private final String DATA_NATION = "US";

    @Autowired
    InterestStockService service;

    @Autowired
    StockService service2;

    @Autowired
    PropertiesUtils propertiesUtils;


    @Test
    public void interestStock_Save_List() {

        // Stock 객체 생성
        StockSaveRequestDto stockSaveRequestDto1 = StockSaveRequestDto.builder()
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

        StockSaveRequestDto stockSaveRequestDto2 = StockSaveRequestDto.builder()
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

        Long stockResult1 = service2.save(stockSaveRequestDto1);
        Long stockResult2 = service2.save(stockSaveRequestDto2);

        Stock stock1 = service2.getOne(stockResult1);
        Stock stock2 = service2.getOne(stockResult2);

        // 등록 요청객체 생성
        InterestStockSaveRequestDto requestDto2 = InterestStockSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .ticker(DATA_TICKER)
                .build();

        // 등록 요청객체 생성
        InterestStockSaveRequestDto requestDto3 = InterestStockSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .ticker(DATA_TICKER2)
                .build();

        // 저장
        Long result = service.save(requestDto2);
        Long result2 = service.save(requestDto3);
        System.out.println(">>> result="+result);

        // 저장 목록 조회 및 출력
        /*
        List<InterestStockResponseDto> responseDtos = service.findByEmail(DATA_EMAIL);
        for(InterestStockResponseDto dto : responseDtos) {
            System.out.println(">>> dto.getEmail()="+dto.getEmail()+
                    ", dto.getTicker()="+dto.getStock().getTicker()+
                    ", dto.getStockName()="+dto.getStock().getStockName()+
                    ", dto.getNation()="+dto.getStock().getNation()+
                    ", dto.getCurrentPrice()="+dto.getStock().getCurrentPrice()

            );
        }

         */

        // Stock 저장 목록 조회 및 출력
        List<StockResponseDto> responseDtos2 = service2.findAll();
        for(StockResponseDto dto : responseDtos2) {
            System.out.println(">>> dto.getTicker()="+dto.getTicker()+
                    ", dto.getStockName()="+dto.getStockName()+
                    ", dto.getNation()="+dto.getNation()+
                    ", dto.getCurrentPrice()="+dto.getCurrentPrice()
            );
        }

        // 저장된 객체의 id가 1이면 통과
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void interestStock_Modify_List() {

        this.interestStock_Save_List();

        Long key = 1L;

        // id값으로 저장된 객체 조회
        InterestStockResponseDto responseDto = service.findById(key);
        StockResponseDto stockResponseDto = responseDto.getStock();

        // Stock 수정 요청 객체 생성 및 수정대상 항목에 수정된 값 반영
        // stock name 값 변경
        String modifyStockName = "Iphone Inc.";
        StockModifyRequestDto stockModifyRequestDto = StockModifyRequestDto.builder()
                .stockId(stockResponseDto.getStockId())
                .ticker(stockResponseDto.getTicker())
                .stockName(modifyStockName)
                .build();

        // 수정된 객체로 저장
        Long result2 = service2.modify(stockModifyRequestDto);
        System.out.println(">>> result2="+result2);

        // 저장 목록 조회 및 출력
        /*
        List<InterestStockResponseDto> responseDtos = service.findByEmail(DATA_EMAIL);
        for(InterestStockResponseDto dto : responseDtos) {
            System.out.println(">>> 수정 후 dto.getEmail()="+dto.getEmail()+
                    ", dto.getTicker()="+dto.getStock().getTicker()+
                    ", dto.getStockName()="+dto.getStock().getStockName()+
                    ", dto.getNation()="+dto.getStock().getNation()+
                    ", dto.getCurrentPrice()="+dto.getStock().getCurrentPrice()
            );
        }

         */

        // Stock 저장 목록 조회 및 출력
        List<StockResponseDto> responseDtos2 = service2.findAll();
        for(StockResponseDto dto : responseDtos2) {
            System.out.println(">>> 수정후 Stock dto.getTicker()="+dto.getTicker()+
                    ", dto.getStockName()="+dto.getStockName()+
                    ", dto.getNation()="+dto.getNation()+
                    ", dto.getCurrentPrice()="+dto.getCurrentPrice()
            );
        }

        /*
        // 첫번째 저장된 객체의 id가 1이면 통과
        assertThat(responseDtos.get(0).getInterestStockId()).isEqualTo(1);

        // 첫번째 저장된 객체의 name이 수정 된 name과 동일하면 통과
        assertThat(responseDtos.get(0).getStock().getStockName()).isEqualTo(modifyStockName);

        // 첫번째 저장된 객체의 email이 수정 전 email과 동일하면 통과
        assertThat(responseDtos.get(0).getEmail()).isEqualTo(DATA_EMAIL);

         */
    }

    @Test
    @Transactional
    public void interestStock_Remove_List() {

        this.interestStock_Save_List();

        Long key = 1L;

        // id값으로 저장된 객체 조회
        InterestStock entity = service.findEntityById(key);

        // 연관관계 삭제
        entity.update(entity.getEmail(), null);

        // 객체 삭제
        service.remove(key);

        // 저장 목록 조회 및 출력
        /*
        List<InterestStockResponseDto> responseDtos = service.findByEmail(DATA_EMAIL);
        for(InterestStockResponseDto dto : responseDtos) {
            System.out.println(">>> 삭제 후 dto.getEmail()="+dto.getEmail()+
                    ", dto.getTicker()="+dto.getStock().getTicker()+
                    ", dto.getStockName()="+dto.getStock().getStockName()+
                    ", dto.getNation()="+dto.getStock().getNation()+
                    ", dto.getCurrentPrice()="+dto.getStock().getCurrentPrice()
            );
        }

         */

        // Stock 저장 목록 조회 및 출력
        List<StockResponseDto> responseDtos2 = service2.findAll();
        for(StockResponseDto dto : responseDtos2) {
            System.out.println(">>> 삭제 후 Stock dto.getTicker()="+dto.getTicker()+
                    ", dto.getStockName()="+dto.getStockName()+
                    ", dto.getNation()="+dto.getNation()+
                    ", dto.getCurrentPrice()="+dto.getCurrentPrice()
            );
        }

        /*
        // 저장된 객체가 1개이면 통과
        assertThat(responseDtos.size()).isEqualTo(1);

        // 저장된 Stock 객체가 2개이면 통과
        assertThat(responseDtos2.size()).isEqualTo(2);


        // 저장된 객체의 name이 2번 name과 동일하면 통과
        assertThat(responseDtos.get(0).getStock().getStockName()).isEqualTo(DATA_STOCK_NAME2);

         */
    }



}
