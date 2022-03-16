package com.nuritech.stock.mystock.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuritech.stock.mystock.domain.InterestStock;
import com.nuritech.stock.mystock.domain.Stock;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockModifyRequestDto;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockResponseDto;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockSaveRequestDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioSaveRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockModifyRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import com.nuritech.stock.mystock.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InterestStockApiControllerTest {

    @Autowired
    InterestStockApiController interestStockApiController;

    @Autowired
    StockApiController stockApiController;

    @Autowired
    StockService stockService;

    @Autowired
    private ObjectMapper mapper; // 객체를 json 형식으로 변경 시 사용

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String _BASE_URL = "http://localhost:";

    private final String DATA_EMAIL = "mintaeho75@gmail.com";

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
    private final BigDecimal DATA_DIVIDEND_GROWTH = null;
    private final BigDecimal DATA_FIVEYEAR_GROWTH_RATE = null;
    private final BigDecimal DATA_PAYOUT_RATIO = null;
    private final BigDecimal DATA_FIVEYEAR_AVG_DIVIDEND_YITELD = null;
    private final String DATA_NATION = "US";

    @BeforeEach
    void setUp() {
        // dto 준비
        // Stock 객체 생성
        StockSaveRequestDto stockDto1 = StockSaveRequestDto.builder()
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

        StockSaveRequestDto stockDto2 = StockSaveRequestDto.builder()
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

        Long stockResult1 = stockApiController.save(stockDto1);
        long stockResult2 = stockApiController.save(stockDto2);

        Stock stock1 = stockService.getOne(stockResult1);
        Stock stock2 = stockService.getOne(stockResult2);

        // 등록 요청객체 생성
        InterestStockSaveRequestDto requestDto1 = InterestStockSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .ticker(DATA_TICKER)
                .build();

        // 등록 요청객체 생성
        InterestStockSaveRequestDto requestDto2 = InterestStockSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .ticker(DATA_TICKER2)
                .build();

        // 저장
        Long result = interestStockApiController.save(requestDto1);
        Long result2 = interestStockApiController.save(requestDto2);
        System.out.println(">>> result=" + result + ", result2="+result2);
        this.printData("등록");
    }

    @Test
    public void interestStock_save_get() throws Exception {

        // 결과 조회
        /*
        List<InterestStockResponseDto> responseDtos = interestStockApiController.findByEmail(DATA_EMAIL);
        Long result = responseDtos.get(0).getInterestStockId();

        assertThat(result).isEqualTo(3L);

        InterestStockResponseDto dto = interestStockApiController.findById(result);
        System.out.println(">>>> findById::id=" + dto.getInterestStockId() +
                ", email=" + dto.getEmail() +
                ", name=" + dto.getStock().getStockName());

        assertThat(dto.getEmail()).isEqualTo(DATA_EMAIL);
        assertThat(dto.getStock().getStockName()).isEqualTo(DATA_STOCK_NAME);

         */
    }

    /**
     * TestRestTemplate 을 이용한 Controller 테스트
     * @throws Exception
     */
    @Test
    public void interestStock_save_get2() throws Exception {

        String _mappingPath  = "/api/v1/interestStock";

        /*
        StockSaveRequestDto stockDto3 = StockSaveRequestDto.builder()
                .ticker(DATA_TICKER3)
                .stockName(DATA_STOCK_NAME3)
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
                .dividendGrowthRate(DATA_DIVIDEND_GROWTH)
                .fiveyearGrowthRate(DATA_FIVEYEAR_GROWTH_RATE)
                .payoutRatio(DATA_PAYOUT_RATIO)
                .fiveyearAvgDividendYield(DATA_FIVEYEAR_AVG_DIVIDEND_YITELD)
                .nation(DATA_NATION)
                .build();

        Long stockResult3 = stockApiController.save(stockDto3);

        Stock stock3 = stockService.findEntityById(stockResult3);
        */
        InterestStockSaveRequestDto requestDto2 = InterestStockSaveRequestDto.builder()
                .email(DATA_EMAIL)
                .ticker(DATA_TICKER3)
                .build();

        String url = _BASE_URL + port + _mappingPath;

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto2, Long.class);

        // 결과 조회
        this.printData("TestRestTemplate이용");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

    }

    @Test
    public void interestStock_modify_get() throws Exception {

        // dto 준비
        String modifyTicker = "UPD_AAPL";
        String modifyStockName = "수정_애플";

        InterestStockResponseDto responseDto = interestStockApiController.findById(3L);

        StockModifyRequestDto stockModifyRequestDto = StockModifyRequestDto.builder()
                .stockId(responseDto.getStock().getStockId())
                .ticker(modifyTicker)
                .stockName(modifyStockName)
                .build();

        Long resultStock = stockApiController.modify(stockModifyRequestDto);

        // 결과 조회
        this.printData("수정 후");

        assertThat(resultStock).isEqualTo(1L);

        InterestStockResponseDto dto = interestStockApiController.findById(3L);
        System.out.println(">>>> findById:: id=" + dto.getInterestStockId() +
                ", email=" + dto.getEmail() +
                ", stock name=" + dto.getStock().getStockName());

        assertThat(dto.getEmail()).isEqualTo(DATA_EMAIL);
        assertThat(dto.getStock().getStockName()).isEqualTo(modifyStockName);
    }

    @Test
    public void interestStock_remove_get() throws Exception {

        // 1번 삭제
        interestStockApiController.remove(3L);

        // 결과 조회
        this.printData("삭제 후");
        /*
        List<InterestStockResponseDto> dtos = interestStockApiController.findByEmail(DATA_EMAIL);
        Long result = dtos.get(0).getInterestStockId();

        assertThat(result).isEqualTo(4L);

         */

    }


    private void printData(String mode) {

        // 저장 목록 조회 및 출력
        /*
        List<InterestStockResponseDto> responseDtos = interestStockApiController.findByEmail(DATA_EMAIL);
        for(InterestStockResponseDto dto : responseDtos) {
            System.out.println(">>> ["+mode+"] dto.getEmail()="+dto.getEmail()+
                    ", dto.getTicker()="+dto.getStock().getTicker()+
                    ", dto.getStockName()="+dto.getStock().getStockName()+
                    ", dto.getNation()="+dto.getStock().getNation()+
                    ", dto.getCurrentPrice()="+dto.getStock().getCurrentPrice()

            );
        }

         */

        // Stock 저장 목록 조회 및 출력
        List<StockResponseDto> responseDtos2 = stockApiController.findAll();
        for(StockResponseDto dto : responseDtos2) {
            System.out.println(">>> ["+mode+"] dto.getTicker()="+dto.getTicker()+
                    ", dto.getStockName()="+dto.getStockName()+
                    ", dto.getNation()="+dto.getNation()+
                    ", dto.getCurrentPrice()="+dto.getCurrentPrice()
            );
        }

    }

}
