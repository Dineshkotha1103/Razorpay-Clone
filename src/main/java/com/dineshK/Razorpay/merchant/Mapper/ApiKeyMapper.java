package com.dineshK.Razorpay.merchant.Mapper;

import com.dineshK.Razorpay.merchant.dto.response.ApiKeyCreateResponse;
import com.dineshK.Razorpay.merchant.dto.response.ApiKeyResponse;
import com.dineshK.Razorpay.merchant.entity.APIKey;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApiKeyMapper {

    ApiKeyCreateResponse toCreateResponse(APIKey apiKey);

    List<ApiKeyResponse> toResponseList(List<APIKey> apiKeyList);
}
