package com.nuritech.stock.mystock.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode implements EnumModel {
    //public final static String[] NOT_LOGGED_IN = {"40001", "로그인 하지 않은 사용자 요청"};

    // COMMON
    NOT_LOGGED_IN("40001", "로그인 하지 않은 사용자 요청"),
    MISSING_REQUIRED_VALUES("50001","필수 파라미터 누락")
    ;

    private String code;
    private String message;

    ErrorCode(String code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getKey() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.message;
    }

}
