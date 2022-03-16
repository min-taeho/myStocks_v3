package com.nuritech.stock.mystock.notice.dto;

import com.nuritech.stock.mystock.notice.domain.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeSaveRequestDto {
    private Integer id;
    private String title;
    private String contents;
    private String contentsType;

    @Builder
    public NoticeSaveRequestDto(Integer id,
                                String title,
                                String contents,
                                String contentsType) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.contentsType = contentsType;
    }

    public Notice toEntity() {
        return Notice.builder()
                .id(id)
                .title(title)
                .contents(contents)
                .contentsType(contentsType)
                .build();
    }
}
