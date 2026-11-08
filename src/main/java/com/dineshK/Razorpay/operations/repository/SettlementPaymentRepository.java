package com.dineshK.Razorpay.operations.repository;

import com.dineshK.Razorpay.operations.entity.SettlementPayment;
import com.dineshK.Razorpay.operations.entity.SettlementPaymentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementPaymentRepository extends JpaRepository<SettlementPayment, SettlementPaymentId> {
}
