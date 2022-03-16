package com.nuritech.stock.mystock.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor //need default constructor for JSON Parsing
public class RefreshTokenUpdateRequestDto implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private Long userId;
    private String accessToken;
    private String refreshToken;

    @Builder
    public RefreshTokenUpdateRequestDto(Long userId,
                                        String accessToken,
                                        String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}