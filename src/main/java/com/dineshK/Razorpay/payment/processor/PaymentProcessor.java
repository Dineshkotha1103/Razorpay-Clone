package com.dineshK.Razorpay.payment.processor;

import com.dineshK.Razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.dineshK.Razorpay.payment.processor.dto.PaymentProcessorResponse;

public interface PaymentProcessor {

    PaymentProcessorResponse charge(PaymentProcessorRequest request);
}
