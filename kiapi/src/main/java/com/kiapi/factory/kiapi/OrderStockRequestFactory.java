package com.kiapi.factory.kiapi;

import com.kiapi.api.OrderType;
import com.kiapi.dto.kiapi.request.OrderStockRequest;
import com.kiapi.dto.request.DomesticStockOnMarkerPriceRequest;

public class OrderStockRequestFactory {
    public static OrderStockRequest StockOnMarketPriceRequest(String[] account, DomesticStockOnMarkerPriceRequest request) {
        return OrderStockRequest.builder()
                .account(account)
                .ticker(request.getTicker())
                .quantity(request.getQuantity())
                .orderType(OrderType.MARKET_PRICE.getCode())
                .price("0")
                .build();
    }
}
