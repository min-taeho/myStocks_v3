package com.nuritech.stock.mystock.user.dto;

import com.nuritech.stock.mystock.user.domain.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private String email;
    private Long userId;
    private String userName;

    public UserResponseDto(UserEntity entity) {
        this.email = entity.getEmail();
        this.userId = entity.getUserId();
        this.userName = entity.getUsername();
    }

}
