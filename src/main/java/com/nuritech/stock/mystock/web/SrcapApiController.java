package com.nuritech.stock.mystock.web;

import com.nuritech.stock.mystock.dto.stock.StockModifyRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import com.nuritech.stock.mystock.service.ScrapService;
import com.nuritech.stock.mystock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SrcapApiController {

    private final ScrapService scrapService;

    /**
     * 주식 정보를 스크래핑한다.
     */
    @PutMapping("/api/v1/scrap")
    public void scraping() {
        scrapService.scrap();
    }


}
