package com.nuritech.stock.mystock.service;

import com.nuritech.stock.mystock.common.util.PropertiesUtils;
import com.nuritech.stock.mystock.domain.*;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockResponseDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockSaveRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import com.nuritech.stock.mystock.dto.tradingNote.TradingNoteModifyRequestDto;
import com.nuritech.stock.mystock.dto.tradingNote.TradingNoteResponseDto;
import com.nuritech.stock.mystock.dto.tradingNote.TradingNoteSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TradingNoteService {

    private final TradingNoteRepository repository;
    private final StockRepository stockRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioStockRepository portfolioStockRepository;

    private final PropertiesUtils propertiesUtils;

    /**
     * TradingNote 객체를 저장한다.
     * @param requestDto
     * @return
     */
    @Transactional
    public Long save(TradingNoteSaveRequestDto requestDto) {
        Stock stock = stockRepository.findByTicker(requestDto.getTicker());
        if (ObjectUtils.isEmpty(stock)) {
            System.out.println(">>>> stock 추가...");
            StockSaveRequestDto stockSaveRequestDto = StockSaveRequestDto.builder()
                    .ticker(requestDto.getTicker())
                    .stockName(requestDto.getTicker())
                    .build();
            stock = stockRepository.save(stockSaveRequestDto.toEntity());
        }
        //Portfolio portfolio = portfolioRepository.getOne(requestDto.getPortfolioId());
        //PortfolioStock portfolioStock = portfolioStockRepository.findByStockAndPortfolio(stock, portfolio);
        /*
        if (ObjectUtils.isEmpty(portfolioStock)) {
            PortfolioStockSaveRequestDto portfolioStockSaveRequestDto = PortfolioStockSaveRequestDto.builder()
                    .portfolio(portfolio)
                    .stock(stock)
                    .build();
            portfolioStock = portfolioStockRepository.save(portfolioStockSaveRequestDto.toEntity());
        }
        */

        requestDto.setStock(stock);
        return repository.save(requestDto.toEntity()).getId();
    }

    /**
     * TradingNote 객체를 수정한다.
     * @param requestDto
     * @return
     */
    @Transactional
    public Long modify(TradingNoteModifyRequestDto requestDto) {
        System.out.println(">>> TradingNoteService::modify::requestDto.getTradingNoteId="+requestDto.getTradingNoteId());

        TradingNote entity = repository.findById(requestDto.getTradingNoteId())
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+requestDto.getTradingNoteId()));

        // requestDto null 값중에서 TradingNote 의 값으로 채워줌
        PropertiesUtils.copyNonNullProperties(entity, requestDto);

        // PortfolioStock 연관관계 FK 반영
        Stock stock = stockRepository.findByTicker(requestDto.getTicker());
        if (ObjectUtils.isEmpty(stock)) {
            StockSaveRequestDto stockSaveRequestDto = StockSaveRequestDto.builder()
                    .ticker(requestDto.getTicker())
                    .stockName(requestDto.getTicker())
                    .build();
            stock = stockRepository.save(stockSaveRequestDto.toEntity());
        }
        /*
        Portfolio portfolio = portfolioRepository.getOne(requestDto.getPortfolioId());
        PortfolioStock portfolioStock = portfolioStockRepository.findByStockAndPortfolio(stock, portfolio);

        requestDto.setPortfolioStock(portfolioStock);
        */

        entity.update(requestDto.getEmail(),
                stock,
                requestDto.getPortfolioId(),
                requestDto.getTradingDate(),
                requestDto.getTradingType(),
                requestDto.getStockNum(),
                requestDto.getUnitPrice(),
                requestDto.getTradingAmount(),
                requestDto.getFee(),
                requestDto.getExchangeRate(),
                requestDto.getNation());

        return entity.getId();
    }

    /**
     * TradingNote 객체를 삭제한다.
     * @param tradingNoteId
     */
    @Transactional
    public void remove(Long tradingNoteId) {
        TradingNote entity = repository.findById(tradingNoteId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+tradingNoteId));
        repository.delete(entity);
    }


    /**
     * TradingNote 객체를 id로 조회한다.
     * @param tradingNoteId
     * @return
     */
    @Transactional(readOnly = true)
    public TradingNoteResponseDto findById(Long tradingNoteId) {
        TradingNote entity = repository.findById(tradingNoteId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+tradingNoteId));
        return new TradingNoteResponseDto(entity);
    }

    /**
     * TradingNote 엔티티를 id로 조회한다.
     * @param tradingNoteId
     * @return
     */
    @Transactional(readOnly = true)
    public TradingNote findEntityById(Long tradingNoteId) {
        TradingNote entity = repository.findById(tradingNoteId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+tradingNoteId));
        return entity;
    }

    /**
     * TradingNote 객체 리스트를 조회한다.
     * @return
     */
    @Transactional(readOnly = true)
    public List<TradingNoteResponseDto> findByEmail(String email) {
        return repository.findByEmailOrderByTradingDateDesc(email).stream()
                .map(TradingNoteResponseDto::new)
                .collect(Collectors.toList());
    }
}
