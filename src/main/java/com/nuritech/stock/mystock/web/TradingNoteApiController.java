package com.nuritech.stock.mystock.web;

import com.nuritech.stock.mystock.dto.tradingNote.TradingNoteModifyRequestDto;
import com.nuritech.stock.mystock.dto.tradingNote.TradingNoteResponseDto;
import com.nuritech.stock.mystock.dto.tradingNote.TradingNoteSaveRequestDto;
import com.nuritech.stock.mystock.service.TradingNoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TradingNoteApiController {

    private final TradingNoteService tradingNoteService;

    /**
     * 매매일지를 등록한다.
     *
     * @param requestDto
     * @return 등록된 매매일지 id
     */
    @PostMapping("/api/v1/tradingNote")
    public Long save(@RequestBody TradingNoteSaveRequestDto requestDto) {
        System.out.println(">>> TradingNoteApiController::save::requestDto.toString()="+requestDto.toString());
        // 필수입력값 체크 로직 추가 필요
        return tradingNoteService.save(requestDto);
    }

    /**
     * 매매일지를 수정한다.
     *
     * @param requestDto
     * @return 수정된 매매일지 id
     */
    @PutMapping("/api/v1/tradingNote")
    public Long modify(@RequestBody TradingNoteModifyRequestDto requestDto) {
        // 필수입력값 체크 로직 추가 필요
        System.out.println(">>> TradingNoteApiController::modify::requestDto.toString()="+requestDto.toString());
        return tradingNoteService.modify(requestDto);
    }

    /**
     * 매매일지를 삭제한다.
     *
     * @param id
     */
    @DeleteMapping("/api/v1/tradingNote/{id}")
    public void remove(@PathVariable Long id) {
        // 필수입력값 체크 로직 추가 필요
        tradingNoteService.remove(id);
    }

    /**
     * 매매일지 id로 매매일지을 조회한다.
     *
     * @param id
     * @return 매매일지 응답 객체
     */
    @GetMapping("/api/v1/tradingNote/{id}")
    public TradingNoteResponseDto findById(@PathVariable Long id) {
        // 필수입력값 체크 로직 추가 필요
        return tradingNoteService.findById(id);
    }

    /**
     * 이메일로 매매일지 리스트를 조회한다.
     *
     * @param email
     * @return 매매일지 응답 객체 리스트
     */
    @GetMapping("/api/v1/tradingNote/byEmail")
    public List<TradingNoteResponseDto> findByEmail(@RequestParam("email") String email) {
        // 필수입력값 체크 로직 추가 필요
        return tradingNoteService.findByEmail(email);
    }
}
