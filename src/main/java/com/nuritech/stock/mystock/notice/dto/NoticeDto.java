package com.nuritech.stock.mystock.notice.dto;

import com.nuritech.stock.mystock.notice.domain.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeDto {
    private Integer id;
    private String title;
    private String contents;
    private String contentsType;
    private LocalDateTime modifiedDate;

    public NoticeDto(Notice entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.contentsType = entity.getContentsType();
        this.modifiedDate = entity.getModifiedDate();
    }

    @Builder
    public NoticeDto(Integer id,
                     String title,
                     String contents,
                     String contentsType,
                     LocalDateTime modifiedDate) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.contentsType = contentsType;
        this.modifiedDate = modifiedDate;
    }

}


