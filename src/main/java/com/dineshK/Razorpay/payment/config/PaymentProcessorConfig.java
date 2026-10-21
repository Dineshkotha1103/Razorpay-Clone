package com.dineshK.Razorpay.payment.config;

import com.dineshK.Razorpay.common.enums.PaymentMethod;
import com.dineshK.Razorpay.payment.gateway.adapter.CardPaymentAdapter;
import com.dineshK.Razorpay.payment.gateway.adapter.NetBankingAdapter;
import com.dineshK.Razorpay.payment.gateway.adapter.UpiPaymentAdapter;
import com.dineshK.Razorpay.payment.processor.PaymentProcessor;
import com.dineshK.Razorpay.payment.processor.strategy.CardPaymentProcessor;
import com.dineshK.Razorpay.payment.processor.strategy.NetBankingPaymentProcessor;
import com.dineshK.Razorpay.payment.processor.strategy.UpiPaymentProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@RequiredArgsConstructor
public class PaymentProcessorConfig {

    private final CardPaymentProcessor cardPaymentProcessor;
    private final UpiPaymentProcessor upiPaymentProcessor;
    private final NetBankingPaymentProcessor netBankingPaymentProcessor;

    @Bean
    public Map<PaymentMethod, PaymentProcessor> paymentProcessorMap(){
        return Map.of(
                PaymentMethod.CARD, cardPaymentProcessor,
                PaymentMethod.UPI, upiPaymentProcessor,
                PaymentMethod.NETBANKING, netBankingPaymentProcessor
        );
    }
}
