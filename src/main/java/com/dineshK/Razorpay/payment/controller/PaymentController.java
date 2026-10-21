package com.dineshK.Razorpay.payment.controller;

import com.dineshK.Razorpay.merchant.security.MerchantContext;
import com.dineshK.Razorpay.payment.dto.request.PaymentInitRequest;
import com.dineshK.Razorpay.payment.dto.response.PaymentResponse;
import com.dineshK.Razorpay.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/v1/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final MerchantContext merchantContext;
    private final PaymentService paymentService;

    @PostMapping("/intiate")
    public ResponseEntity<PaymentResponse> initiate(@Valid @RequestBody PaymentInitRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.intiate(merchantContext.getMerchantId(), request));
    }

    @PostMapping("/{paymentId}/capture")
    public ResponseEntity<PaymentResponse> capture(@PathVariable UUID paymentId){
        return ResponseEntity.ok(paymentService.capture(merchantContext.getMerchantId(), paymentId));
    }
}
