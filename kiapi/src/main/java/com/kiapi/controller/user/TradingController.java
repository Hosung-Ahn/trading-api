package com.kiapi.controller.user;

import com.kiapi.api.kiApiService;
import com.kiapi.dto.request.DomesticStockOnMarkerPriceRequest;
import com.kiapi.entity.member.Member;
import com.kiapi.security.userDetails.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class TradingController {
    private final kiApiService kiApiService;
    @GetMapping("balance")
    public ResponseEntity<?> getBalance() {
        Member member = getMember();

        return ResponseEntity.ok(kiApiService.getDomesticBalance(member));
    }

    @PostMapping("order-domestic")
    public ResponseEntity<?> orderDomesticStockOnMarketPrice(@RequestBody DomesticStockOnMarkerPriceRequest request) {
        Member member = getMember();
        return ResponseEntity.ok(kiApiService.orderDomesticStockOnMarketPrice(member, request));
    }

    private Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        return principal.getMember();
    }
}
