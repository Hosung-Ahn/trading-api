package com.kiapi.dto.kiapi.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kiapi.dto.request.DomesticStockOnMarkerPriceRequest;
import lombok.Data;

@Data
public class OrderStockRequest {
    @JsonProperty("CANO")
    private String frontAccount;
    @JsonProperty("ACNT_PRDT_CD")
    private String backAccount;
    @JsonProperty("PDNO")
    private String ticker;
    // Enum 으로 주문 구분을 나누어야 할 필요가 있습니다.
    @JsonProperty("ORD_DVSN")
    private String orderCategory;
    @JsonProperty("ORD_QTY")
    private String quantity;
    // 한 주당 가격을 의미합니다. 시장가 매수와 같이 주문 가격을 입력할 필요가 없는 경우는 "0" 으로 입력합니다.
    @JsonProperty("ORD_UNPR")
    private String price;

    // 팩토리 객체를 구현하여 주식 주문 종류별로 다른 객체가 생성되도록 할 필요가 있습니다.
    public OrderStockRequest(String[] account, String ticker, String orderCategory, String quantity, String price) {
        this.frontAccount = account[0];
        this.backAccount = account[1];
        this.ticker = ticker;
        this.orderCategory = orderCategory;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderStockRequest(String[] account, DomesticStockOnMarkerPriceRequest requestDto) {
        this.frontAccount = account[0];
        this.backAccount = account[1];
        this.ticker = requestDto.getTicker();
        this.orderCategory = "01";
        this.quantity = requestDto.getQuantity();
        this.price = "0";
    }
}
