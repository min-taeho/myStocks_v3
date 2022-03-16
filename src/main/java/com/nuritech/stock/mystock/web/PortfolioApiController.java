package com.nuritech.stock.mystock.web;

import com.nuritech.stock.mystock.domain.Portfolio;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioModifyRequestDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioResponseDto;
import com.nuritech.stock.mystock.dto.portfolio.PortfolioSaveRequestDto;
import com.nuritech.stock.mystock.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PortfolioApiController {

    private final PortfolioService portfolioService;

    /**
     * 포트폴리오를 등록한다.
     *
     * @param requestDto
     * @return 등록된 포트폴리오 id
     */
    @PostMapping("/api/v1/portfolio")
    public Long save(@RequestBody PortfolioSaveRequestDto requestDto) {
        // 필수입력값 체크 로직 추가 필요
        return portfolioService.save(requestDto);
    }

    /**
     * 포트폴리오를 수정한다.
     *
     * @param requestDto
     * @return 수정된 포트폴리오 id
     */
    @PutMapping("/api/v1/portfolio")
    public Long modify(@RequestBody PortfolioModifyRequestDto requestDto) {
        // 필수입력값 체크 로직 추가 필요
        return portfolioService.modify(requestDto);
    }

    /**
     * 포트폴리오를 삭제한다.
     *
     * @param id
     */
    @DeleteMapping("/api/v1/portfolio/{id}")
    public void remove(@PathVariable Long id) {
        // 필수입력값 체크 로직 추가 필요
        portfolioService.remove(id);
    }

    /**
     * 포트폴리오 id로 포트폴리오를 조회한다.
     *
     * @param id
     * @return 포트폴리오 응답 객체
     */
    @GetMapping("/api/v1/portfolio/{id}")
    public PortfolioResponseDto findById(@PathVariable Long id) {
        // 필수입력값 체크 로직 추가 필요
        return portfolioService.findById(id);
    }

    /**
     * 이메일과 포트폴리오 명으로 포트폴리오를 조회한다.
     *
     * @param email
     * @param name
     * @return 포트폴리오 응답 객체
     */
    @GetMapping("/api/v1/portfolio/byEmailAndName")
    public PortfolioResponseDto findByEmailAndName(@RequestParam("email") String email,
                                                   @RequestParam("name") String name) {
        // 필수입력값 체크 로직 추가 필요
        return portfolioService.findByEmailAndName(email, name);
    }

    /**
     * 이메일로 포트폴리오 리스트를 조회한다.
     *
     * @param email
     * @return 포트폴리오 응답 객체 리스트
     */
    @GetMapping("/api/v1/portfolio/byEmail")
    public List<PortfolioResponseDto> findByEmail(@RequestParam("email") String email) {
        // 필수입력값 체크 로직 추가 필요
        log.debug(">>> findByEmail parameter email="+email);
        return portfolioService.findByEmail(email);
    }
}
