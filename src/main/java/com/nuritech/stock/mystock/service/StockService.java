package com.nuritech.stock.mystock.service;

import com.nuritech.stock.mystock.common.util.PropertiesUtils;
import com.nuritech.stock.mystock.domain.*;
import com.nuritech.stock.mystock.dto.stock.StockModifyRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository repository;
    private final PortfolioStockRepository portfolioStockRepository;
    private final PropertiesUtils propertiesUtils;

    /**
     * Stock 객체를 저장한다.
     * @param requestDto
     * @return
     */
    @Transactional
    public Long save(StockSaveRequestDto requestDto) {
        return repository.save(requestDto.toEntity()).getId();
    }

    /**
     * Stock 객체를 수정한다.
     * @param requestDto
     * @return
     */
    @Transactional
    public Long modify(StockModifyRequestDto requestDto) {
        Stock entity = repository.findById(requestDto.getStockId())
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+requestDto.getStockId()));

        List<PortfolioStock> portfolioStocks = portfolioStockRepository.findByStock(entity);

        // requestDto null 값중에서 stock의 값으로 채워줌
        PropertiesUtils.copyNonNullProperties(entity, requestDto);
        entity.update(requestDto.getTicker(),
                requestDto.getStockName(),
                requestDto.getBusinessCycle(),
                requestDto.getSector(),
                requestDto.getCurrentPrice(),
                requestDto.getHighestPrice(),
                requestDto.getLowerPrice(),
                requestDto.getDesiredPurchasePrice(),
                requestDto.getNobilityStockYn(),
                requestDto.getDividendPayMonth(),
                requestDto.getCompanyInfo(),
                requestDto.getAnnualPayout(),
                requestDto.getDividendYield(),
                requestDto.getDividendGrowth(),
                requestDto.getFiveyearGrowthRate(),
                requestDto.getPayoutRatio(),
                requestDto.getFiveyearAvgDividendYield(),
                requestDto.getNation());

        // PortfolioStock도 update 함
        portfolioStocks.forEach(portfolioStock -> {
            portfolioStock.update(portfolioStock.getPortfolio(), entity, portfolioStock.getStockNum(), portfolioStock.getUnitPrice());
        });


        return entity.getId();
    }

    /**
     * Stock 객체를 삭제한다.
     * @param stockId
     */
    @Transactional
    public void remove(Long stockId) {
        Stock entity = repository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+stockId));
        repository.delete(entity);
    }

    /**
     * Stock 객체를 id로 조회한다.
     * @param stockId
     * @return
     */
    @Transactional(readOnly = true)
    public StockResponseDto findById(Long stockId) {
        Stock entity = repository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+stockId));
        return new StockResponseDto(entity);
    }

    /**
     * Stock 객체를 ticker로 조회한다.
     * @param ticker
     * @return
     */
    @Transactional(readOnly = true)
    public StockResponseDto findByTicker(String ticker) {
        return new StockResponseDto(this.findEntityByTicker(ticker));
    }

    /**
     * Stock 엔티티를 id로 조회한다.
     * @param stockId
     * @return
     */
    @Transactional(readOnly = true)
    public Stock findEntityById(Long stockId) {
        Stock entity = repository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+stockId));
        return entity;
    }

    /**
     * Stock 객체 참조를 id로 조회한다.
     * @param stockId
     * @return
     */
    @Transactional(readOnly = true)
    public Stock getOne(Long stockId) {
        return repository.getOne(stockId);

    }

    /**
     * Stock 엔티티를 ticker로 조회한다.
     * @param ticker
     * @return
     */
    @Transactional(readOnly = true)
    public Stock findEntityByTicker(String ticker) {
        return repository.findByTicker(ticker);
    }

    /**
     * Stock 객체의 리스트를 조회한다.
     * @return
     */
    @Transactional(readOnly = true)
    public List<StockResponseDto> findAll() {
        return repository.findAll().stream()
                .map(StockResponseDto::new)
                .collect(Collectors.toList());
    }
}
