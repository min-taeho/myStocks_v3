package com.nuritech.stock.mystock.service;

import com.nuritech.stock.mystock.common.util.PropertiesUtils;
import com.nuritech.stock.mystock.domain.*;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockModifyRequestDto;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockResponseDto;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockSaveRequestDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioSaveRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class InterestStockService {

    private final InterestStockRepository repository;
    private final StockRepository stockRepository;
    private final PropertiesUtils propertiesUtils;

    /**
     * InterestStock 객체를 저장한다.
     * @param requestDto
     * @return
     */
    @Transactional
    public Long save(InterestStockSaveRequestDto requestDto) {

        Stock stock = stockRepository.findByTicker(requestDto.getTicker());
        if (ObjectUtils.isEmpty(stock)) {

            StockSaveRequestDto stockSaveRequestDto = StockSaveRequestDto.builder()
                    .ticker(requestDto.getTicker())
                    .stockName(requestDto.getTicker())
                    .build();
            stock = stockRepository.save(stockSaveRequestDto.toEntity());
        }
        requestDto.setStock(stock);

        return repository.save(requestDto.toEntity()).getId();
    }

    /**
     * InterestStock 객체를 수정한다.
     * @param requestDto
     * @return
     */
    @Transactional
    public Long modify(InterestStockModifyRequestDto requestDto) {
        InterestStock entity = repository.findById(requestDto.getInterestStockId())
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+requestDto.getInterestStockId()));

        // requestDto의 항목중 null 값을 가진 항목을 대상으로 InterestStock 값으로 채워줌
        PropertiesUtils.copyNonNullProperties(entity, requestDto);
        entity.update(requestDto.getEmail(), requestDto.getStock().toEntity());

        return entity.getId();
    }

    /**
     * InterestStock 객체를 삭제한다.
     * @param interestStockId
     */
    @Transactional
    public void remove(Long interestStockId) {
        InterestStock entity = repository.findById(interestStockId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+interestStockId));
        repository.delete(entity);
    }


    /**
     * InterestStock Id로 InterestStock 객체를 조회한다.
     * @param interestStockId
     * @return
     */
    @Transactional(readOnly = true)
    public InterestStockResponseDto findById(Long interestStockId) {
        InterestStock entity = repository.findById(interestStockId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+interestStockId));
        return new InterestStockResponseDto(entity);
    }

    /**
     * 이메일로 InterestStock 객체 리스트를 조회한다.
     * @return
     */
    @Transactional(readOnly = true)
    public List<InterestStockResponseDto> findByEmail(String email,
                                                      Sort.Direction direction,
                                                      String sortBy) {
        return repository.findByEmail(email, Sort.by(direction, sortBy)).stream()
                .map(InterestStockResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * InterestStock Id로  InterestStock 엔티티를 조회한다.
     * @param interestStockId
     * @return
     */
    @Transactional(readOnly = true)
    public InterestStock findEntityById(Long interestStockId) {
        InterestStock entity = repository.findById(interestStockId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+interestStockId));
        return entity;
    }
}
