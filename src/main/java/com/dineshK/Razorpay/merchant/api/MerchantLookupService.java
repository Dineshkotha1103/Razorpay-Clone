package com.dineshK.Razorpay.merchant.api;

import com.dineshK.Razorpay.common.dto.SettlementBankDetails;
import com.dineshK.Razorpay.common.dto.WebhookTarget;

import java.util.List;
import java.util.UUID;

public interface MerchantLookupService {

    List<WebhookTarget> getActiveConfigsForEvent(UUID merchantId, String eventType);

    List<UUID> listActiveMerchantIds();

    SettlementBankDetails getSettlementBankDetails(UUID merchantId);

}
