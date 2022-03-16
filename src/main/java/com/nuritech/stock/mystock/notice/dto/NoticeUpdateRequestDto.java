package com.nuritech.stock.mystock.notice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeUpdateRequestDto {
    private String title;
    private String contents;
    private String contentsType;

    @Builder
    public NoticeUpdateRequestDto(String title,
                                  String contents,
                                  String contentsType) {
        this.title = title;
        this.contents = contents;
        this.contentsType = contentsType;
    }

}
