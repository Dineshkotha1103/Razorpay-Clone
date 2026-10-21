package com.dineshK.Razorpay.payment.service;

import com.dineshK.Razorpay.payment.dto.request.PaymentInitRequest;
import com.dineshK.Razorpay.payment.dto.response.PaymentResponse;

import java.util.UUID;

public interface PaymentService {

    PaymentResponse intiate(UUID merchantId, PaymentInitRequest request);

    PaymentResponse capture(UUID merchantId, UUID paymentId);

    void resolveAuthorization(UUID paymentId, boolean approve, String bankRef, String errorCode, String errorDescription);
}
