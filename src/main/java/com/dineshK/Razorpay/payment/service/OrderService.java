package com.dineshK.Razorpay.payment.service;

import com.dineshK.Razorpay.payment.dto.request.CreateOrderRequest;
import com.dineshK.Razorpay.payment.dto.response.OrderResponse;
import com.dineshK.Razorpay.payment.dto.response.PaymentResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface OrderService {
    OrderResponse create(UUID merchantId, CreateOrderRequest request);

    OrderResponse getById(UUID merchantId, UUID orderId);

    OrderResponse cancel(UUID merchantId, UUID orderId);

    List<PaymentResponse> listPayments(UUID merchantId, UUID orderId);
}
