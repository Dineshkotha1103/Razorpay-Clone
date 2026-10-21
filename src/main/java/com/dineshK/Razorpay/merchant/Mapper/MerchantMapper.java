package com.dineshK.Razorpay.merchant.Mapper;

import com.dineshK.Razorpay.merchant.dto.request.MerchantSignupRequest;
import com.dineshK.Razorpay.merchant.dto.response.MerchantResponse;
import com.dineshK.Razorpay.merchant.entity.Merchant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchantMapper {

    Merchant toEntitySignUpRequest(MerchantSignupRequest request);

    MerchantResponse toResponse(Merchant merchant);
}
