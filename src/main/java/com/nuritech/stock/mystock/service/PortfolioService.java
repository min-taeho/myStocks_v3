package com.nuritech.stock.mystock.service;

import com.nuritech.stock.mystock.common.util.PropertiesUtils;
import com.nuritech.stock.mystock.domain.*;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioSaveRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockModifyRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import com.nuritech.stock.mystock.dto.stock.StockSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PortfolioService {

    private final PortfolioRepository repository;
    private final PropertiesUtils propertiesUtils;

    /**
     * Portfolio 객체를 저장한다.
     * @param requestDto
     * @return
     */
    @Transactional
    public Long save(PortfolioSaveRequestDto requestDto) {
        return repository.save(requestDto.toEntity()).getId();
    }

    /**
     * Portfolio 객체를 수정한다.
     * @param requestDto
     * @return
     */
    @Transactional
    public Long modify(PortfolioModifyRequestDto requestDto) {
        Portfolio entity = repository.findById(requestDto.getPortfolioId())
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+requestDto.getPortfolioId()));

        // requestDto null 값중에서 Portfolio의 값으로 채워줌
        PropertiesUtils.copyNonNullProperties(entity, requestDto);
        entity.update(null, requestDto.getEmail(), requestDto.getName());

        return entity.getId();
    }

    /**
     * Portfolio 객체를 삭제한다.
     * @param portfolioId
     */
    @Transactional
    public void remove(Long portfolioId) {
        Portfolio entity = repository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+portfolioId));
        repository.delete(entity);
    }


    /**
     * Portfolio 객체를 id로 조회한다.
     * @param portfolioId
     * @return
     */
    @Transactional(readOnly = true)
    public PortfolioResponseDto findById(Long portfolioId) {
        Portfolio entity = repository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+portfolioId));
        return new PortfolioResponseDto(entity);
    }

    /**
     * Portfolio 객체를 이메일과 이름으로 조회한다.
     * @param email
     * @param name
     * @return
     */
    @Transactional(readOnly = true)
    public PortfolioResponseDto findByEmailAndName(String email, String name) {
        Portfolio entity = this.findEntityByEmailAndName(email, name);
        return new PortfolioResponseDto(entity);
    }

    /**
     * Portfolio 엔티티를 id로 조회한다.
     * @param portfolioId
     * @return
     */
    @Transactional(readOnly = true)
    public Portfolio findEntityById(Long portfolioId) {
        Portfolio entity = repository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("There is no such data. id="+portfolioId));
        return entity;
    }

    /**
     * Portfolio 엔티티를 이메일과 이름으로 조회한다.
     * @param email
     * @param name
     * @return
     */
    @Transactional(readOnly = true)
    public Portfolio findEntityByEmailAndName(String email, String name) {
        return repository.findByEmailAndName(email, name);
    }

    /**
     * 이메일로 Portfolio 객체 리스트를 조회한다.
     * @return
     */
    @Transactional(readOnly = true)
    public List<PortfolioResponseDto> findByEmail(String email) {
        return repository.findByEmail(email).stream()
                .map(PortfolioResponseDto::new)
                .collect(Collectors.toList());
    }
}
