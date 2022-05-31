package com.nuritech.stock.mystock.user.dto;

import com.nuritech.stock.mystock.user.domain.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String authId;
    private String email;
    private String password;

    public UserResponseDto(UserEntity entity) {
        this.userId = entity.getUserId();
        this.authId = entity.getAuthId();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
    }

}