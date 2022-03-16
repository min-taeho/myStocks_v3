package com.nuritech.stock.mystock.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioSaveRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockModifyRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
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
public class StockApiControllerTest {

    @Autowired
    StockApiController stockApiController;

    @Autowired
    private ObjectMapper mapper; // 객체를 json 형식으로 변경 시 사용

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String _BASE_URL = "http://localhost:";


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
    private final String DATA_NATION = "";


    @BeforeEach
    void setUp() {
        // dto 준비
        StockSaveRequestDto requestDto1 = StockSaveRequestDto.builder()
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

        // 등록
        Long result = stockApiController.save(requestDto1);
        Long result2 = stockApiController.save(requestDto2);

        this.printData("등록");
    }

    @Test
    public void stock_save_get() throws Exception {

        // 결과 조회
        StockResponseDto responseDto = stockApiController.findByTicker(DATA_TICKER);
        Long result = responseDto.getStockId();

        assertThat(result).isEqualTo(1L);

        StockResponseDto dto = stockApiController.findById(result);
        System.out.println(">>>> findById::id=" + dto.getStockId() +
                ", ticker=" + dto.getTicker() +
                ", name=" + dto.getStockName());

        assertThat(dto.getTicker()).isEqualTo(DATA_TICKER);
        assertThat(dto.getStockName()).isEqualTo(DATA_STOCK_NAME);
        assertThat(dto.getStockId()).isEqualTo(1L);
    }

    /**
     * TestRestTemplate 을 이용한 Controller 테스트
     * @throws Exception
     */
    @Test
    public void stock_save_get2() throws Exception {

        String _mappingPath  = "/api/v1/stock";

        StockSaveRequestDto requestDto3 = StockSaveRequestDto.builder()
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
                .dividendGrowth(DATA_DIVIDEND_GROWTH)
                .fiveyearGrowthRate(DATA_FIVEYEAR_GROWTH_RATE)
                .payoutRatio(DATA_PAYOUT_RATIO)
                .fiveyearAvgDividendYield(DATA_FIVEYEAR_AVG_DIVIDEND_YITELD)
                .nation(DATA_NATION)
                .build();

        String url = _BASE_URL + port + _mappingPath;

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto3, Long.class);

        // 결과 조회
        this.printData("TestRestTemplate이용");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

    }

    @Test
    public void stock_modify_get() throws Exception {

        // dto 준비
        String modifyTicker = "UPD_AAPL";
        String modifyStockName = "수정_애플";

        StockResponseDto responseDto = stockApiController.findByTicker(DATA_TICKER);

        StockModifyRequestDto modifyRequestDto = StockModifyRequestDto.builder()
                .stockId(responseDto.getStockId())
                .ticker(modifyTicker)
                .stockName(modifyStockName)
                .currentPrice(new BigDecimal(300))
                .build();

        Long result = stockApiController.modify(modifyRequestDto);
        // 결과 조회
        this.printData("수정 후");

        assertThat(result).isEqualTo(1L);

        StockResponseDto dto = stockApiController.findById(result);
        System.out.println(">>>> findById:: id=" + dto.getStockId() +
                ", ticker=" + dto.getTicker() +
                ", name=" + dto.getStockName());

        assertThat(dto.getTicker()).isEqualTo(modifyTicker);
        assertThat(dto.getStockName()).isEqualTo(modifyStockName);
    }

    @Test
    public void stock_remove_get() throws Exception {

        // 1번 삭제
        stockApiController.remove(1L);

        // 결과 조회
        this.printData("삭제 후");

        StockResponseDto dto = stockApiController.findByTicker(DATA_TICKER2);
        Long result = dto.getStockId();

        assertThat(result).isEqualTo(2L);

    }


    private void printData(String mode) {
        // 저장목록 조회 및 출력
        List<StockResponseDto> responseDtos = stockApiController.findAll();
        for(StockResponseDto dto : responseDtos) {
            System.out.println(">>> ["+mode+"] dto.getTicker()="+dto.getTicker()+
                            ", dto.getCurrentPrice()="+dto.getCurrentPrice() +
                    ", dto.getStockName()="+dto.getStockName());
        }
    }

}
