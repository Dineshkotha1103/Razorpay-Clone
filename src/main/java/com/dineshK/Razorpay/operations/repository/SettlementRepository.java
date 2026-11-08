package com.dineshK.Razorpay.operations.repository;

import com.dineshK.Razorpay.common.enums.SettlementStatus;
import com.dineshK.Razorpay.operations.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SettlementRepository extends JpaRepository<Settlement, UUID> {
    List<Settlement> findByStatus(SettlementStatus settlementStatus);
}
