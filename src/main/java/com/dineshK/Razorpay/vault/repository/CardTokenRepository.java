package com.dineshK.Razorpay.vault.repository;

import com.dineshK.Razorpay.vault.entity.CardToken;
import com.dineshK.Razorpay.vault.entity.VaultCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardTokenRepository extends JpaRepository<CardToken, UUID> {

    Optional<CardToken> findByTokenAndRevokedAtIsNull(String token);
}
