package com.kiapi.dto.request;

import lombok.Data;

@Data
public class DomesticStockOnMarkerPriceRequest {
    private String ticker;
    private String quantity;
}
