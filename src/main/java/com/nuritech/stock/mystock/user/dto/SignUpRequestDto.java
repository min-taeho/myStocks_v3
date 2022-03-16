package com.nuritech.stock.mystock.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignUpRequestDto {
    private String email;
    private String password;
    private String auth;
}