package com.dineshK.Razorpay.merchant.service;

import com.dineshK.Razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.dineshK.Razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.dineshK.Razorpay.merchant.dto.response.ApiKeyResponse;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.UUID;


public interface ApiKeyService {

    ApiKeyCreateResponse create(UUID merchantId, CreateApiKeyRequest request);

    List<ApiKeyResponse> listByMerchant(UUID merchantId);

    void revoke(UUID merchantId, UUID keyId);

    @Nullable ApiKeyCreateResponse rotate(UUID merchantId, UUID keyId);
}
