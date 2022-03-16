package com.nuritech.stock.mystock.service;

import com.nuritech.stock.mystock.common.util.PropertiesUtils;
import com.nuritech.stock.mystock.domain.*;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioSaveRequestDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockResponseDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockSaveRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PortfolioStockService {

    private final PortfolioStockRepository repository;
    private final PortfolioRepository portfolioRepository;
    private final StockRepository stockRepository;
    private final PropertiesUtils propertiesUtils;

    /**
     * PortfolioStock 객체를 저장한다.
     * @param requestDto
     * @return
     */
    @Transactional
    public Long save(PortfolioStockSaveRequestDto requestDto) {
        System.out.println(">> save  메소드 시작");
        Stock stock = stockRepository.findByTicker(requestDto.getTicker());
        if (ObjectUtils.isEmpty(stock)) {
            System.out.println(">>>> Stock is null이니까 새로 추가");
            StockSaveRequestDto stockSaveRequestDto = StockSaveRequestDto.builder()
                    .ticker(requestDto.getTicker())
                    .stockName(requestDto.getTicker())
                    .build();
            stock = stockRepository.save(stockSaveRequestDto.toEntity());
        }

        Portfolio portfolio = portfolioRepository.getOne(requestDto.getPortfolioId());

        requestDto.setPortfolio(portfolio);
        requestDto.setStock(stock);

        return repository.save(requestDto.toEntity()).getId();
    }

    /**
     * PortfolioStock 객체를 수정한다.
     * @param requestDto
     * @return
     */

    @Transactional
    public Long modify(PortfolioStockModifyRequestDto requestDto) {
        PortfolioStock entity = repository.findById(requestDto.getPortfolioStockId())
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+requestDto.getPortfolioStockId()));

        // requestDto null 값중에서 Portfolio의 값으로 채워줌
        PropertiesUtils.copyNonNullProperties(entity, requestDto);
        entity.update(requestDto.getPortfolio(), requestDto.getStock(), requestDto.getStockNum(), requestDto.getUnitPrice());

        return entity.getId();
    }

    /**
     * PortfolioStock 객체를 삭제한다.
     *
     * @param portfolioStockId
     */
    @Transactional
    //public void remove(Long portfolioId, String ticker) {
    public void remove(Long portfolioStockId) {

        PortfolioStock entity = repository.findById(portfolioStockId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+portfolioStockId));
        repository.delete(entity);
    }


    /**
     * PortfolioStock 객체를 id로 조회한다.
     * @param portfolioStockId
     * @return
     */
    @Transactional(readOnly = true)
    public PortfolioStockResponseDto findById(Long portfolioStockId) {
        PortfolioStock entity = repository.findById(portfolioStockId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+portfolioStockId));
        return new PortfolioStockResponseDto(entity);
    }

    /**
     * PortfolioStock 엔티티를 id로 조회한다.
     * @param portfolioStockId
     * @return
     */
    @Transactional(readOnly = true)
    public PortfolioStock findEntityById(Long portfolioStockId) {
        PortfolioStock entity = repository.findById(portfolioStockId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+portfolioStockId));
        return entity;
    }

    /**
     * PortfolioStock 엔티티를 Stock과 Portfolio로 조회한다.
     * @param stock
     * @param portfolio
     * @return
     */
    @Transactional(readOnly = true)
    public PortfolioStock findEntityByStockAndPortfolio(Stock stock, Portfolio portfolio) {
        return repository.findByStockAndPortfolio(stock, portfolio);
    }

    /**
     * PortfolioStock 객체 리스트를 조회한다.
     * @return
     */
    @Transactional(readOnly = true)
    public List<PortfolioStockResponseDto> findAll() {
        return repository.findAll().stream()
                .map(PortfolioStockResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * PortfolioStock 객체를 Portfolio id로 조회한다.
     * @param portfolioId
     * @return
     */
    @Transactional(readOnly = true)
    public List<PortfolioStockResponseDto> findStockByPortfolioId(String email,
                                                                  Long portfolioId,
                                                                  Sort.Direction direction,
                                                                  String sortBy) {

        Portfolio portfolio = portfolioRepository.findByIdAndEmail(portfolioId, email);
        List<PortfolioStock> entities = repository.findByPortfolio(portfolio, Sort.by(direction, sortBy));

        List<PortfolioStockResponseDto> list = new ArrayList<PortfolioStockResponseDto>();
        entities.forEach(entity -> {
            log.debug("PortfolioStockService::findStockByPortfolioId::entity={}", entity.toString());
            list.add(new PortfolioStockResponseDto(entity));
        });
        /*
        List<PortfolioStockResponseDto> list = new ArrayList<PortfolioStockResponseDto>();
        for(PortfolioStock entity : entities) {
            list.add(new PortfolioStockResponseDto(entity));
        }
        */

        for(PortfolioStockResponseDto dto : list) {
            log.debug(">>> dto="+dto.toString());
        }

        return list;

    }

}
