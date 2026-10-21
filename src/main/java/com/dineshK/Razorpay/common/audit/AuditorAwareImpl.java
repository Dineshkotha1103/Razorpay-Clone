package com.dineshK.Razorpay.common.audit;

import com.dineshK.Razorpay.merchant.security.MerchantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final MerchantContext merchantContext;

    @Override
    public Optional<String> getCurrentAuditor() {

        try{
            String keyId = merchantContext.getKeyId();
            if (keyId != null && !keyId.isBlank()) return Optional.of(keyId);
            if (merchantContext.getKeyId() != null){
                return Optional.of("merchant_id: " + merchantContext.getMerchantId());
            }
        } catch (Exception ignored){

        }
        return Optional.of("SYSTEM");
    }
}
