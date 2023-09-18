package com.kiapi.api;

public enum TradeId {
    BUY("VTTC0802U"), SELL("VTTC0801U");
    private final String code;

    TradeId(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static TradeId fromType(String type) {
        if (type.equals("buy")) {
            return BUY;
        } else if (type.equals("sell")) {
            return SELL;
        }
        throw new IllegalArgumentException("주문 타입은 \"buy\" \"sell\" 중 하나입니다. 주문 타입 :  " + type);
    }
}
