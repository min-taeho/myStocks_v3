package com.nuritech.stock.mystock.web;

import com.nuritech.stock.mystock.dto.portfolio.PortfolioModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioSaveRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockModifyRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import com.nuritech.stock.mystock.service.PortfolioService;
import com.nuritech.stock.mystock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class StockApiController {

    private final StockService stockService;

    /**
     * Stock를 등록한다.
     *
     * @param requestDto
     * @return 등록된 Stock id
     */
    @PostMapping("/api/v1/stock")
    public Long save(@RequestBody StockSaveRequestDto requestDto) {
        // 필수입력값 체크 로직 추가 필요
        return stockService.save(requestDto);
    }

    /**
     * Stock을 수정한다.
     *
     * @param requestDto
     * @return 수정된 Stock id
     */
    @PutMapping("/api/v1/stock")
    public Long modify(@RequestBody StockModifyRequestDto requestDto) {
        System.out.println(">>>StockApiController::modify::requestDto.toString="+requestDto.toString());
        // 필수입력값 체크 로직 추가 필요
        return stockService.modify(requestDto);
    }

    /**
     * Stock을 삭제한다.
     *
     * @param id
     */
    @DeleteMapping("/api/v1/stock/{id}")
    public void remove(@PathVariable Long id) {
        // 필수입력값 체크 로직 추가 필요
        stockService.remove(id);
    }

    /**
     * Stock id로 Stock을 조회한다.
     *
     * @param id
     * @return Stock 응답 객체
     */
    @GetMapping("/api/v1/stock/{id}")
    public StockResponseDto findById(@PathVariable Long id) {
        // 필수입력값 체크 로직 추가 필요
        return stockService.findById(id);
    }

    /**
     * Ticker로 Stock을 조회한다.
     *
     * @param ticker
     * @return Stock 응답 객체
     */
    @GetMapping("/api/v1/stock/byTicker")
    public StockResponseDto findByTicker(@RequestParam("ticker") String ticker) {
        // 필수입력값 체크 로직 추가 필요
        return stockService.findByTicker(ticker);
    }

    /**
     * Stock 목록을 조회한다.
     *
     * @return Stock 응답 객체 리스트
     */
    @GetMapping("/api/v1/stock")
    public List<StockResponseDto> findAll() {
        // 필수입력값 체크 로직 추가 필요
        return stockService.findAll();
    }




}
