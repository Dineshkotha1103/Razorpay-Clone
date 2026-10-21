package com.dineshK.Razorpay.payment.controller;

import com.dineshK.Razorpay.merchant.security.MerchantContext;
import com.dineshK.Razorpay.payment.dto.request.CreateOrderRequest;
import com.dineshK.Razorpay.payment.dto.response.OrderResponse;
import com.dineshK.Razorpay.payment.service.OrderService;
import com.dineshK.Razorpay.payment.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final MerchantContext merchantContext;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> create(@PathVariable @Valid CreateOrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(merchantContext.getMerchantId(), request));
    }
}
