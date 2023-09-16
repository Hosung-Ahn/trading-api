package com.kiapi.dto.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccessTokenRequest {
    @JsonProperty("grant_type")
    private String grantType;
    private String appkey;
    @JsonProperty("appsecret")
    private String secretKey;

    public AccessTokenRequest(String appkey, String secretKey) {
        this.grantType = "client_credentials";
        this.appkey = appkey;
        this.secretKey = secretKey;
    }
}
