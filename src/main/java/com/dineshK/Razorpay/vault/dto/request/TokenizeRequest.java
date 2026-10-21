package com.dineshK.Razorpay.vault.dto.request;

import com.dineshK.Razorpay.vault.validation.ExpiryYear;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.LuhnCheck;

import java.util.UUID;

public record TokenizeRequest(

        @NotBlank(message = "PAN is required")
        @LuhnCheck(message = "Invalid card number")
        @Pattern(regexp = "^[0-9]{13,19}$")
        String pan,

        @NotBlank(message = "CVV is required")
        @Pattern(regexp = "^[0-9]{3,4}$")
        String cvv,

        @NotNull(message = "Expiry month is required")
        @Min(value = 1, message = "Expiry month must be in between 1 to 12")
        @Max(value = 1, message = "Expiry month must be in between 1 to 12")
        Integer expiryMonth,

        @NotNull(message = "Expiry year is required")
        @ExpiryYear
        Integer expiryYear,

        UUID customerId,

        @Size(min = 3, message = "Name must be at least 3 characters long")
        String cardHolderName
) {


}
