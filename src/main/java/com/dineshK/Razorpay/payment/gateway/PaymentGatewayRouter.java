package com.dineshK.Razorpay.payment.gateway;

import com.dineshK.Razorpay.common.enums.PaymentMethod;
import com.dineshK.Razorpay.payment.gateway.dto.PaymentRequest;
import com.dineshK.Razorpay.payment.gateway.dto.PaymentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentGatewayRouter {
    private final Map<PaymentMethod, PaymentAdapter> paymentAdapters;

    public PaymentResult initiate(PaymentRequest request){
        PaymentAdapter adapter = paymentAdapters.get(request.method());
        if (adapter == null){
            throw new IllegalArgumentException("No payment adapter registered for the method: "+ request.method());
        }
        return adapter.initiate(request);

    }

    public PaymentResult capture(PaymentMethod method, UUID paymentId) {
        PaymentAdapter adapter = paymentAdapters.get(method);
        if (adapter == null){
            throw new IllegalArgumentException("No payment adapter registered for the method: "+ method);
        }
        return adapter.capture(paymentId);
    }
}
