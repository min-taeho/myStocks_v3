package com.nuritech.stock.mystock.web;

import com.nuritech.stock.mystock.domain.InterestStock;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockModifyRequestDto;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockResponseDto;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockSaveRequestDto;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockModifyRequestDto;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockResponseDto;
import com.nuritech.stock.mystock.dto.interestStock.InterestStockSaveRequestDto;
import com.nuritech.stock.mystock.service.InterestStockService;
import com.nuritech.stock.mystock.service.InterestStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class InterestStockApiController {

    private final InterestStockService interestStockService;

    /**
     * 관심종목을 등록한다.
     *
     * @param requestDto
     * @return 등록된 관심종목 id
     */
    @PostMapping("/api/v1/interestStock")
    public Long save(@RequestBody InterestStockSaveRequestDto requestDto) {
        // 필수입력값 체크 로직 추가 필요
        System.out.println("requestDto.getEmail="+requestDto.getEmail()+", getTicker()="+requestDto.getTicker());
        return interestStockService.save(requestDto);
    }

    /**
     * 관심종목을 수정한다.
     *
     * @param requestDto
     * @return 수정된 관심종목 id
     */
    @PutMapping("/api/v1/interestStock")
    public Long modify(@RequestBody InterestStockModifyRequestDto requestDto) {
        // 필수입력값 체크 로직 추가 필요
        return interestStockService.modify(requestDto);
    }

    /**
     * 관심종목을 삭제한다.
     *
     * @param id
     */
    @DeleteMapping("/api/v1/interestStock/{id}")
    public void remove(@PathVariable Long id) {
        // 필수입력값 체크 로직 추가 필요
        interestStockService.remove(id);
    }

    /**
     * 관심종목 id로 관심종목을 조회한다.
     *
     * @param id
     * @return 관심종목 응답 객체
     */
    @GetMapping("/api/v1/interestStock/{id}")
    public InterestStockResponseDto findById(@PathVariable Long id) {
        // 필수입력값 체크 로직 추가 필요
        return interestStockService.findById(id);
    }

    /**
     * 이메일로 관심종목 리스트를 조회한다.
     *
     * @param email
     * @return 관심종목 응답 객체 리스트
     */
    @GetMapping("/api/v1/interestStock/byEmail")
    public List<InterestStockResponseDto> findByEmail(@RequestParam("email") String email,
                                                      @RequestParam("sortBy") String sortBy,
                                                      @RequestParam("sortDirection") String sortDirection) {

        log.debug("InterestStockApiController::findByEmail::sortBy={}, sorDirection={}", sortBy, sortDirection);
        Sort.Direction direction = "Desc".equals(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;

        // 필수입력값 체크 로직 추가 필요
        return interestStockService.findByEmail(email, direction, sortBy);
    }
}
