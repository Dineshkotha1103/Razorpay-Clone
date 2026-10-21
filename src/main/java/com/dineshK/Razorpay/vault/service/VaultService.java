package com.dineshK.Razorpay.vault.service;

import com.dineshK.Razorpay.common.entity.Money;
import com.dineshK.Razorpay.payment.processor.dto.PaymentProcessorResponse;
import com.dineshK.Razorpay.vault.dto.request.TokenizeRequest;
import com.dineshK.Razorpay.vault.dto.response.TokenizeResponse;

import java.util.Map;
import java.util.UUID;

public interface VaultService {
    TokenizeResponse tokenize(TokenizeRequest request, UUID merchantId);

    PaymentProcessorResponse charge(UUID paymentId, String token, Money amount, Map<String, Object> methodDetails);
}
