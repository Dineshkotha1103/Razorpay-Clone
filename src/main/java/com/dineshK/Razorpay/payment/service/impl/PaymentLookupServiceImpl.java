package com.dineshK.Razorpay.payment.service.impl;

import com.dineshK.Razorpay.common.enums.PaymentStatus;
import com.dineshK.Razorpay.payment.api.PaymentLookupService;
import com.dineshK.Razorpay.payment.entity.Payment;
import com.dineshK.Razorpay.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentLookupServiceImpl implements PaymentLookupService {

    private final PaymentRepository paymentRepository;

    @Override
    public List<Payment> findUnsettledCapturedPayments(UUID merchantId) {
        return paymentRepository.findByMerchantIdAndStatusForUpdate(merchantId, PaymentStatus.CAPTURED);
    }
}
