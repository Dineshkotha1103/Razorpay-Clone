package com.dineshK.Razorpay.payment.config;

import com.dineshK.Razorpay.common.enums.PaymentMethod;
import com.dineshK.Razorpay.payment.gateway.PaymentAdapter;
import com.dineshK.Razorpay.payment.gateway.adapter.CardPaymentAdapter;
import com.dineshK.Razorpay.payment.gateway.adapter.NetBankingAdapter;
import com.dineshK.Razorpay.payment.gateway.adapter.UpiPaymentAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class PaymentAdapterConfig {

    private final NetBankingAdapter netBankingAdapter;
    private final CardPaymentAdapter cardPaymentAdapter;
    private final UpiPaymentAdapter upiPaymentAdapter;

    @Bean
    public Map<PaymentMethod, PaymentAdapter> paymentAdapterMap(){
        return Map.of(
                PaymentMethod.CARD, cardPaymentAdapter,
                PaymentMethod.UPI, upiPaymentAdapter,
                PaymentMethod.NETBANKING, netBankingAdapter
        );
    }
}
