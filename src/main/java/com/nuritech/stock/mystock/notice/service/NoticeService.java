package com.nuritech.stock.mystock.notice.service;

import com.nuritech.stock.mystock.notice.domain.Notice;
import com.nuritech.stock.mystock.notice.domain.NoticeRepository;
import com.nuritech.stock.mystock.notice.dto.NoticeSaveRequestDto;
import com.nuritech.stock.mystock.notice.dto.NoticeUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;


    // 등록
    @Transactional
    public Notice add(NoticeSaveRequestDto requestDto) {
        return noticeRepository.save(requestDto.toEntity());
    }

    // 조회
    @Transactional
    public List<Notice> getAll() {
        return noticeRepository.findAll();
    }

    // 조회-단건
    @Transactional
    public Notice getId(Integer id) {
        return noticeRepository.getOne(id);
    }

    // 삭제-단건
    @Transactional
    public void remove(Integer id) {
        noticeRepository.deleteById(id);
    }

    // 삭제
    @Transactional
    public void removeAll() {
         noticeRepository.deleteAll();
    }

    // 수정
    @Transactional
    public Integer modify(Integer id,
                         NoticeUpdateRequestDto requestDto) {
        Notice notice = noticeRepository.getOne(id);
        notice.update(requestDto.getTitle(),
                requestDto.getContents(),
                requestDto.getContentsType());
        return id;
    }

    // 조회 - 검색
    @Transactional
    public Page<Notice> searchNotice(String id,
                                     String title,
                                     String contents,
                                     String contentsType,
                                     Date startDt,
                                     Date endDt,
                                     Pageable pageable) {
        return noticeRepository.searchNotice(id,
                title,
                contents,
                contentsType,
                startDt,
                endDt,
                pageable);
    }

}
