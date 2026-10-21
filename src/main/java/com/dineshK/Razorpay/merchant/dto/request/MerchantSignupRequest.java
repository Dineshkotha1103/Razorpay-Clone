package com.dineshK.Razorpay.merchant.dto.request;

import com.dineshK.Razorpay.common.enums.BusinessType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MerchantSignupRequest(
        @NotNull(message = "name is necessary")
        @Size(max = 50, message = "name should not be more than 50 characters")
        String name,

        @Email
        @NotNull(message = "email is required")
        String email,

        @NotNull(message = "password is required")
        @Size(max = 8)
        String password,

        @Size(max = 50, message = "name should not be more than 50 characters")
        String businessName,

        BusinessType businessType

) {
}
