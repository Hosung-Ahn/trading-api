package com.kiapi.api;

import com.kiapi.dto.kiapi.request.AccessTokenRequest;
import com.kiapi.dto.kiapi.request.OrderStockRequest;
import com.kiapi.dto.request.DomesticStockOnMarkerPriceRequest;
import com.kiapi.dto.kiapi.response.AccessTokenResponse;
import com.kiapi.entity.member.Member;
import com.kiapi.security.aes.AESUtil;
import com.kiapi.service.redis.KiApiAccessTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class kiApiService {
    private final KiApiAccessTokenService kiApiAccessTokenService;
    private final AESUtil AESUtil;

    private void issueAccessToken(Member member) {
        // access token 이 없으면 발급 요청
        if (!kiApiAccessTokenService.isExist(member.getId())) {
            setAccessTokenInRedis(member);
        }
    }

    private void setAccessTokenInRedis(Member member) {
        String appKey = AESUtil.decrypt(member.getAppKey());
        String secretKey = AESUtil.decrypt(member.getSecretKey());
        // api 요청이 예외가 발생하지 않는다고 가정하였습니다.
        // 예외가 발생한 상황에 대해 처리할 필요가 있습니다.
        AccessTokenResponse response = WebClient.create()
                .post()
                .uri("https://openapivts.koreainvestment.com:29443/oauth2/tokenP")
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(new AccessTokenRequest(appKey, secretKey))
                .retrieve()
                .bodyToMono(AccessTokenResponse.class)
                .block();

        // access token 을 redis 에 저장합니다.
        kiApiAccessTokenService.setAccessToken(member.getId(), response.getAccessToken(),
                calculateRemainedSeconds(response.getAccessTokenTokenExpired()));
    }

    private Long calculateRemainedSeconds(String expiredDateString) {
        LocalDateTime expiredDate = convertStringToLocalDateTime(expiredDateString);
        return LocalDateTime.now().until(expiredDate, ChronoUnit.SECONDS);
    }

    private LocalDateTime convertStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateString, formatter);
    }

    public String getDomesticBalance(Member member) {
        issueAccessToken(member);
        String accessToken = kiApiAccessTokenService.getAccessToken(member.getId());
        String appKey = AESUtil.decrypt(member.getAppKey());
        String secretKey = AESUtil.decrypt(member.getSecretKey());
        // api 요청이 예외가 발생하지 않는다고 가정하였습니다.
        // 예외가 발생한 상황에 대해 처리할 필요가 있습니다.

        String[] account = AESUtil.decrypt(member.getAccount()).split("-");
        URI uri = UriComponentsBuilder.fromHttpUrl("https://openapivts.koreainvestment.com:29443/uapi/domestic-stock/v1/trading/inquire-balance")
                .queryParam("CANO", account[0])
                .queryParam("ACNT_PRDT_CD", account[1])
                .queryParam("AFHR_FLPR_YN", "N")
                .queryParam("OFL_YN", "")
                .queryParam("INQR_DVSN", "02")
                .queryParam("UNPR_DVSN", "01")
                .queryParam("FUND_STTL_ICLD_YN", "N")
                .queryParam("FNCG_AMT_AUTO_RDPT_YN", "N")
                .queryParam("PRCS_DVSN", "01")
                .queryParam("CTX_AREA_FK100", "")
                .queryParam("CTX_AREA_NK100", "")
                .build().toUri();

        String response = WebClient.create()
                .get()
                .uri(uri)
                .headers(header -> {
                    header.setBearerAuth(accessToken);
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                    header.add("tr_id", "VTTC8434R");
                    header.add("appkey", appKey);
                    header.add("appsecret", secretKey);
                })
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }

    public String orderDomesticStockOnMarketPrice(Member member, DomesticStockOnMarkerPriceRequest requestDto) {
        issueAccessToken(member);
        String accessToken = kiApiAccessTokenService.getAccessToken(member.getId());
        String appKey = AESUtil.decrypt(member.getAppKey());
        String secretKey = AESUtil.decrypt(member.getSecretKey());
        String[] account = AESUtil.decrypt(member.getAccount()).split("-");

        // api 요청이 예외가 발생하지 않는다고 가정하였습니다.
        // 예외가 발생한 상황에 대해 처리할 필요가 있습니다.
        return WebClient.create()
                .post()
                .uri("https://openapivts.koreainvestment.com:29443/uapi/domestic-stock/v1/trading/order-cash")
                .headers(header -> {
                    header.setContentType(MediaType.APPLICATION_JSON);
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                    header.setBearerAuth(accessToken);
                    header.add("tr_id", "VTTC0802U");
                    header.add("appkey", appKey);
                    header.add("appsecret", secretKey);
                })
                .bodyValue(new OrderStockRequest(account, requestDto))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
