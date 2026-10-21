package com.dineshK.Razorpay.merchant.dto.request;

import com.dineshK.Razorpay.common.enums.Environment;

public record CreateApiKeyRequest(
        Environment environment
) {
}
