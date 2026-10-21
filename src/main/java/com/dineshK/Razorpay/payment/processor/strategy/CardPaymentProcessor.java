package com.dineshK.Razorpay.payment.processor.strategy;

import com.dineshK.Razorpay.common.util.RandomizerUtil;
import com.dineshK.Razorpay.payment.processor.PaymentProcessor;
import com.dineshK.Razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.dineshK.Razorpay.payment.processor.dto.PaymentProcessorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CardPaymentProcessor implements PaymentProcessor {

    public static final String PAN_CARD_DECLINED = "4000000000000002";
    public static final String PAN_CARD_EXPIRED = "4000000000000069";
    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {

        String pan = request.pan();

        if (PAN_CARD_DECLINED.equals(pan)){
            log.warn("Card declined");
            return new PaymentProcessorResponse.Failure("CARD DECLINED", "Card rejected by the bank");
        }

        if (PAN_CARD_EXPIRED.equals(pan)){
            log.warn("Pan card expired");
            return new PaymentProcessorResponse.Failure("CARD EXPIRED", "Card has been expired");
        }

        String processorRef = "CARD_PROCESSOR"+ RandomizerUtil.randomBase64(16);

        return new PaymentProcessorResponse.Pending(processorRef);
    }
}
