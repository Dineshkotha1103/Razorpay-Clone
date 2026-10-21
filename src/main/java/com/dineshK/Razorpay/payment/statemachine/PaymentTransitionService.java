package com.dineshK.Razorpay.payment.statemachine;

import com.dineshK.Razorpay.common.enums.PaymentActor;
import com.dineshK.Razorpay.common.enums.PaymentEvent;
import com.dineshK.Razorpay.common.enums.PaymentStatus;
import com.dineshK.Razorpay.payment.entity.Payment;
import com.dineshK.Razorpay.payment.entity.PaymentTransitionLog;
import com.dineshK.Razorpay.payment.repository.PaymentTransitionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentTransitionService {

    private final PaymentTransitionLogRepository paymentTransitionLogRepository;
    private final PaymentStateMachine paymentStateMachine;

    public PaymentStatus apply(Payment payment, PaymentEvent event){
        PaymentStatus next = paymentStateMachine.Transition(payment.getStatus(), event);
        PaymentTransitionLog log = PaymentTransitionLog.builder()
                .payment(payment)
                .fromStatus(payment.getStatus())
                .event(event)
                .toStatus(next)
                .actor(PaymentActor.SYSTEM)
                .occurredAt(LocalDateTime.now())
                .build();
        payment.setStatus(next);

        paymentTransitionLogRepository.save(log);
        return next;
    }
}
