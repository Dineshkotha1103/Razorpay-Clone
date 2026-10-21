package com.dineshK.Razorpay.vault.controller;

import com.dineshK.Razorpay.merchant.security.MerchantContext;
import com.dineshK.Razorpay.vault.dto.request.TokenizeRequest;
import com.dineshK.Razorpay.vault.dto.response.TokenizeResponse;
import com.dineshK.Razorpay.vault.service.VaultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vault")
public class VaultController {

    private final MerchantContext merchantContext;
    private final VaultService vaultService;

    @PostMapping("/tokenize")
    public ResponseEntity<TokenizeResponse> tokenize(@Valid @RequestBody TokenizeRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(vaultService.tokenize(request, merchantContext.getMerchantId()));
    }
}
