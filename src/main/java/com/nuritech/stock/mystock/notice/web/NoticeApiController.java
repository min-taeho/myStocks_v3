package com.nuritech.stock.mystock.notice.web;

import com.nuritech.stock.mystock.notice.domain.Notice;
import com.nuritech.stock.mystock.notice.dto.NoticeSaveRequestDto;
import com.nuritech.stock.mystock.notice.dto.NoticeUpdateRequestDto;
import com.nuritech.stock.mystock.notice.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@Api(tags = "공지사항")
@RequestMapping(value = "/api/v1")
public class NoticeApiController {

    private final NoticeService noticeService;

    @ApiOperation(value = "등록", notes = "등록", tags = "공지사항")
    @PostMapping(value = "/notice")
    @ResponseStatus(value = HttpStatus.OK)
    public Notice add(@RequestBody NoticeSaveRequestDto requestDto) {
        return noticeService.add(requestDto);
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @ApiOperation(value = "조회", notes = "전체 조회", tags = "공지사항")
    @GetMapping(value = "/notice")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Notice> getAll() {
        return noticeService.getAll();
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @ApiOperation(value = "조회", notes = "단건 조회", tags = "공지사항")
    @GetMapping(value = "/notice/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Notice get(@PathVariable Integer id) {
        return noticeService.getId(id);
    }

    @ApiOperation(value = "삭제", notes = "단건 삭제", tags = "공지사항")
    @DeleteMapping(value = "/notice/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void remove(@PathVariable Integer id) {
        noticeService.remove(id);
    }

    @ApiOperation(value = "삭제", notes = "전체 삭제", tags = "공지사항")
    @DeleteMapping(value = "/notice")
    @ResponseStatus(value = HttpStatus.OK)
    public void removeAll() {
        noticeService.removeAll();
    }

    @ApiOperation(value = "수정", notes = "단건 수정", tags = "공지사항")
    @PutMapping(value = "/notice/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Integer modify(@PathVariable Integer id,
                         @RequestBody NoticeUpdateRequestDto requestDto) {
        return noticeService.modify(id, requestDto);
    }

    // 조회 - 검색
    @ManyToMany(fetch = FetchType.LAZY)
    @ApiOperation(value = "조회-검색", notes = "전체 조회-검색", tags = "공지사항")
    @GetMapping(value = "/notice/search")
    @ResponseStatus(value = HttpStatus.OK)
    public Page<Notice> searchNotice(@RequestParam(value = "id", required = false) String id,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "contents", required = false) String contents,
                                     @RequestParam(value = "contentsType", required = false) String contentsType,
                                     @RequestParam(value = "startDt", required = false)
                                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDt,
                                     @RequestParam(value = "endDt", required = false)
                                        @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDt,
                                     @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        log.debug("pageable : {}", pageable.toString());

        return noticeService.searchNotice(id, title, contents, contentsType, startDt, endDt, pageable);
    }


}
