package com.dineshK.Razorpay.payment.mapper;

import com.dineshK.Razorpay.payment.dto.response.PaymentResponse;
import com.dineshK.Razorpay.payment.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

// componentModel = MappingConstants.ComponentModel.SPRING
@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "orderId", source = "order.id")
    PaymentResponse toResponse(Payment payment);

    List<PaymentResponse> toResponseList(List<Payment> paymentList);
}
