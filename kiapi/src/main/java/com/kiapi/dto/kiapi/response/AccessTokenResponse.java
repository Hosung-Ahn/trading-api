package com.kiapi.dto.kiapi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("access_token_token_expired")
    private String accessTokenTokenExpired;
    @JsonProperty("refresh_token")
    private String tokenType;
    @JsonProperty("expires_in")
    private String expiresIn;
}
