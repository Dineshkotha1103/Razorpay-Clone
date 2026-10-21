package com.dineshK.Razorpay.merchant.service;

import com.dineshK.Razorpay.merchant.dto.request.LoginRequest;
import com.dineshK.Razorpay.merchant.dto.request.MerchantSignupRequest;
import com.dineshK.Razorpay.merchant.dto.response.LoginResponse;
import com.dineshK.Razorpay.merchant.dto.response.MerchantResponse;
import jakarta.validation.Valid;

public interface AuthService {
    MerchantResponse signup(MerchantSignupRequest request);

    LoginResponse login(LoginRequest request);
}
