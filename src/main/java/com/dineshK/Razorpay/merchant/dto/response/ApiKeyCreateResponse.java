package com.dineshK.Razorpay.merchant.dto.response;

import java.util.UUID;

public record ApiKeyCreateResponse(

        UUID id,
        String keyId,
        String keySecret,
        com.dineshK.Razorpay.common.enums.Environment environment

) {
}
