package com.dineshK.Razorpay.vault.dto.response;

import com.dineshK.Razorpay.common.enums.CardBrand;

public record TokenizeResponse(

        String token,
        String lastFour,
        CardBrand brand,
        Integer expiryMonth,
        Integer expiryYear
) {
}
