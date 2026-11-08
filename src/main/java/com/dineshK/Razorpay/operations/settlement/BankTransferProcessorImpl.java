package com.dineshK.Razorpay.operations.settlement;

import com.dineshK.Razorpay.common.entity.Money;
import com.dineshK.Razorpay.common.util.RandomizerUtil;
import com.dineshK.Razorpay.operations.settlement.dto.BankTransferResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class BankTransferProcessorImpl implements BankTransferProcessor{
    @Override
    public BankTransferResult initiate(UUID settlementId, UUID merchantId, Money amount, String bankAccount, String ifsc) {
        String registrationRef = "TXN_"+ RandomizerUtil.randomBase64(12);
        log.debug("Bank Transfer call completed for settlementId: {}, registrationRef: {}", settlementId, registrationRef);
        return new BankTransferResult(registrationRef);
    }
}
