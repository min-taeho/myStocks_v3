package com.nuritech.stock.mystock.user.dto;

import com.nuritech.stock.mystock.user.domain.ApiTokenEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignInResponseDto {
    private Long userId;
    private String accessToken;
    private String refreshToken;

    public SignInResponseDto(ApiTokenEntity entity) {
        this.userId = entity.getUserId();
        this.accessToken = entity.getAccessToken();
        this.refreshToken = entity.getRefreshToken();
    }


}
