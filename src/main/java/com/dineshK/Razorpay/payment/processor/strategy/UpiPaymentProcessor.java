package com.dineshK.Razorpay.payment.processor.strategy;

import com.dineshK.Razorpay.common.util.RandomizerUtil;
import com.dineshK.Razorpay.payment.processor.PaymentProcessor;
import com.dineshK.Razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.dineshK.Razorpay.payment.processor.dto.PaymentProcessorResponse;
import org.springframework.stereotype.Component;

@Component
public class UpiPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        final String VPA_CODE_FAIL = "fail@okaxis";

        String bankCode = request.methodDetails() != null ?
                request.methodDetails().get("vpa").toString() : null;

        if(VPA_CODE_FAIL.equals(bankCode)){
            return new PaymentProcessorResponse.Failure("UPI_REJECTED",
                    "Bank rejected the transaction request");
        }

        String processorRef = "UPI_PROCESSOR"+ RandomizerUtil.randomBase64(16);
        String bankRef = "BANK_REF"+ RandomizerUtil.randomBase64(16);

        return new PaymentProcessorResponse.Success(processorRef, bankRef);
    }
}
