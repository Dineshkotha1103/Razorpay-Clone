package com.dineshK.Razorpay.operations.settlement;

import com.dineshK.Razorpay.common.entity.Money;
import com.dineshK.Razorpay.operations.settlement.dto.BankTransferResult;

import java.util.UUID;

public interface BankTransferProcessor {

    BankTransferResult initiate(UUID settlementId, UUID merchantId, Money amount,
                                String bankAccount, String ifsc);
}
