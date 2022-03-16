package com.nuritech.stock.mystock.user;

import lombok.Getter;

@Getter
public enum Authority {
    USER("USER"),
    ADMIN("ADMIN");

    private String name;

    Authority(String name) {
        this.name = name;
    }
}