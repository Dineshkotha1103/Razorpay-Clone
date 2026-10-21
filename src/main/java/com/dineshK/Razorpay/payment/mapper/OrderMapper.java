package com.dineshK.Razorpay.payment.mapper;

import com.dineshK.Razorpay.payment.dto.response.OrderResponse;
import com.dineshK.Razorpay.payment.entity.OrderRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse toResponse(OrderRecord orderRecord);
}
