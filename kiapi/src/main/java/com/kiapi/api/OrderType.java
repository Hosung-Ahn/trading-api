package com.kiapi.api;

import java.util.Objects;

public enum OrderType {
    MARKET_PRICE("01");

    private final String code;

    OrderType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
