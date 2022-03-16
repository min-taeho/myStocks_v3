package com.nuritech.stock.mystock.service;

import com.nuritech.stock.mystock.common.util.PropertiesUtils;
import com.nuritech.stock.mystock.domain.InterestStock;
import com.nuritech.stock.mystock.domain.InterestStockRepository;
import com.nuritech.stock.mystock.domain.Stock;
import com.nuritech.stock.mystock.domain.StockRepository;
import com.nuritech.stock.mystock.dto.scrap.StockDto;
import com.nuritech.stock.mystock.dto.stock.StockModifyRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScrapService {

    private final StockService stockService;
    private final StockRepository stockRepository;
    //private final InterestStockRepository interestStockRepository;
    //private final PropertiesUtils propertiesUtils;

    /**
     * 미국 주식 정보를 스크래핑한다.
     */
    @Transactional
    public void scrap() {

        // 1. 관심종목 목록을 조회한다.
        List<Stock> list = stockRepository.findAll();

        if (ObjectUtils.isEmpty(list)) {
            System.out.println("There is no data to process.");
            return;
        }

        // 관심종목 각각에 대한 정보를 수집 및 갱신 한다.
        list.forEach(item -> {

            // Stock 엔티티를 StockDto 객체로 변환
            StockDto stock = new StockDto(item);

            // Scrap 정보로 갱신
            stock.setScrapInfo();

            // 갱신된 정보로 저장
            stockService.modify(stock.toStockModifyRequestDto());
        });


    }

}
