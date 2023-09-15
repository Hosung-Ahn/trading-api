package com.kiapi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    private String appKey;
    @NotBlank
    private String secretKey;
    @NotBlank
    private String account;

    public SignupRequest(String username, String email, String password, String appKey, String secretKey, String account) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.appKey = appKey;
        this.secretKey = secretKey;
        this.account = account;
    }
}
