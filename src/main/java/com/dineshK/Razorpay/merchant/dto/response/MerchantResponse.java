package com.dineshK.Razorpay.merchant.dto.response;

import com.dineshK.Razorpay.common.enums.BusinessType;
import com.dineshK.Razorpay.common.enums.MerchantStatus;

import java.util.UUID;

public record MerchantResponse(

        UUID id,
        String name,
        String email,
        String businessName,
        BusinessType businessType,
        MerchantStatus merchantStatus

) {
}
