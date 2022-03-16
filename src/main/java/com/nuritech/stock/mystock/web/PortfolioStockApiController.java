package com.nuritech.stock.mystock.web;

import com.nuritech.stock.mystock.dto.portfolio.PortfolioModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioSaveRequestDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockResponseDto;
import com.nuritech.stock.mystock.dto.portfolioStock.PortfolioStockSaveRequestDto;
import com.nuritech.stock.mystock.dto.stock.StockResponseDto;
import com.nuritech.stock.mystock.service.PortfolioService;
import com.nuritech.stock.mystock.service.PortfolioStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PortfolioStockApiController {

    private final PortfolioStockService portfolioStockService;

    /**
     * Portfolio와 Stock 연계를 등록한다.
     *
     * @param requestDto
     * @return 등록된 PortfolioStock id
     */
    @PostMapping("/api/v1/portfolioStock")
    public Long save(@RequestBody PortfolioStockSaveRequestDto requestDto) {

        System.out.println(">>> requestDto.getPortfolioId="+requestDto.getPortfolioId()+", getTicker()="+requestDto.getTicker());

        // 필수입력값 체크 로직 추가 필요
        return portfolioStockService.save(requestDto);
    }

    /**
     * Portfolio와 Stock 연계를 수정한다.
     *
     * @param requestDto
     * @return 수정된 PortfolioStock id
     */
    @PutMapping("/api/v1/portfolioStock")
    public Long modify(@RequestBody PortfolioStockModifyRequestDto requestDto) {
        // 필수입력값 체크 로직 추가 필요
        return portfolioStockService.modify(requestDto);
    }

    /**
     * Portfolio와 Stock 연계를 삭제한다.
     *
     * @param portfolioStockId
     */
    @DeleteMapping("/api/v1/portfolioStock/{portfolioStockId}")
    public void remove(@PathVariable Long portfolioStockId) {

        // 필수입력값 체크 로직 추가 필요
        portfolioStockService.remove(portfolioStockId);
    }

    /**
     * PortfolioStock id로 PortfolioStock을 조회한다.
     *
     * @param id
     * @return PortfolioStock 응답 객체
     */
    @GetMapping("/api/v1/portfolioStock/{id}")
    public PortfolioStockResponseDto findById(@PathVariable Long id) {
        // 필수입력값 체크 로직 추가 필요
        return portfolioStockService.findById(id);
    }

    /**
     * 이메일과 포트폴리오 id로 포트폴리오에 속한 Stock을 조회한다.
     *
     * @param email
     * @param portfolioId
     * @return
     */
    @GetMapping("/api/v1/portfolioStock/byEmailAndPortfolioId")
    public List<PortfolioStockResponseDto> findStockByPortfolioId(@RequestParam("email") String email,
                                                                  @RequestParam("portfolioId") Long portfolioId,
                                                                  @RequestParam("sortBy") String sortBy,
                                                                  @RequestParam("sortDirection") String sortDirection) {

        log.debug("PortfolioStockApiController::findStockByPortfolioId::sortBy={}, sorDirection={}", sortBy, sortDirection);
        Sort.Direction direction = "Desc".equals(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;

        // 필수입력값 체크 로직 추가 필요
        //return portfolioStockService.findStockByPortfolioId(email, portfolioId);
        List<PortfolioStockResponseDto> list = portfolioStockService.findStockByPortfolioId(email, portfolioId, direction, sortBy);
        return list;


    }


}
