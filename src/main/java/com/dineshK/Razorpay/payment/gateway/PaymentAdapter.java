package com.dineshK.Razorpay.payment.gateway;

import com.dineshK.Razorpay.payment.gateway.dto.PaymentRequest;
import com.dineshK.Razorpay.payment.gateway.dto.PaymentResult;

import java.util.UUID;

public interface PaymentAdapter {
    PaymentResult initiate(PaymentRequest request);

    PaymentResult capture(UUID paymentId);
}
