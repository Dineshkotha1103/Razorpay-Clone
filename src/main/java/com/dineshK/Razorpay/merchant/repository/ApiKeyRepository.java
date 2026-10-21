package com.dineshK.Razorpay.merchant.repository;

import com.dineshK.Razorpay.merchant.entity.APIKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApiKeyRepository extends JpaRepository<APIKey, UUID> {
    List<APIKey> findByMerchant_Id(UUID merchantId);

    Optional<APIKey> findByKeyId(String keyId);
}
