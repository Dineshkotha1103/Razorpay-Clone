package com.dineshK.Razorpay.payment.service.impl;

import com.dineshK.Razorpay.common.enums.EventAggregateType;
import com.dineshK.Razorpay.common.enums.OrderStatus;
import com.dineshK.Razorpay.common.enums.PaymentEvent;
import com.dineshK.Razorpay.common.enums.PaymentStatus;
import com.dineshK.Razorpay.common.exception.BusinessViolationException;
import com.dineshK.Razorpay.common.exception.ResourceNotFoundException;
import com.dineshK.Razorpay.payment.dto.request.PaymentInitRequest;
import com.dineshK.Razorpay.payment.dto.response.PaymentResponse;
import com.dineshK.Razorpay.payment.entity.OrderRecord;
import com.dineshK.Razorpay.payment.entity.Payment;
import com.dineshK.Razorpay.payment.gateway.PaymentGatewayRouter;
import com.dineshK.Razorpay.payment.gateway.dto.PaymentRequest;
import com.dineshK.Razorpay.payment.gateway.dto.PaymentResult;
import com.dineshK.Razorpay.payment.mapper.PaymentMapper;
import com.dineshK.Razorpay.payment.outbox.OutboxEventPublisher;
import com.dineshK.Razorpay.payment.repository.OrderRepository;
import com.dineshK.Razorpay.payment.repository.PaymentRepository;
import com.dineshK.Razorpay.payment.service.PaymentService;
import com.dineshK.Razorpay.payment.statemachine.PaymentTransitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayRouter paymentGatewayRouter;
    private final PaymentMapper paymentMapper;
    private final PaymentTransitionService paymentTransitionService;
    private final OutboxEventPublisher eventPublisher;

    @Override
    @Transactional
    public PaymentResponse intiate(UUID merchantId, PaymentInitRequest request) {
//        OrderRecord order = orderRepository.findByIdAndMerchantId(request.orderID(), merchantId)
//                .orElseThrow(() -> new ResourceNotFoundException("Order", request.orderID()));

        OrderRecord order = orderRepository.findByIdAndMerchantIdForUpdate(request.orderID(), merchantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", request.orderID()));

        if (order.getOrderStatus() != OrderStatus.CREATED && order.getOrderStatus() != OrderStatus.ATTEMPTED){
            throw new BusinessViolationException("ORDER_NOT_PAYABLE",
                    "Order cannot accept payment in this status: "+order.getOrderStatus());
        }

        order.setOrderStatus(OrderStatus.ATTEMPTED);
        order.setAttempts(order.getAttempts()+1);

        Payment payment = Payment.builder()
                .order(order)
                .merchantId(merchantId)
                .amount(order.getAmount())
                .status(PaymentStatus.CREATED)
                .method(request.method())
                .idempotencyKey(UUID.randomUUID().toString())
                .methodDetails(request.methodDetails())
                .build();
        payment = paymentRepository.save(payment);

        PaymentRequest paymentRequest = new PaymentRequest(payment.getId(),
                request.orderID(), merchantId,
                order.getAmount(), request.method(),
                request.methodDetails());

        paymentTransitionService.apply(payment, PaymentEvent.AUTHORIZE_ATTEMPT);
        PaymentResult result = paymentGatewayRouter.initiate(paymentRequest);

        switch (result){
            case PaymentResult.Pending pending -> payment.setProcessorReference(pending.paymentRegistrationRef());
            case PaymentResult.Failure failure -> {
                //payment.setStatus(PaymentStatus.FAILED);
                paymentTransitionService.apply(payment, PaymentEvent.AUTHORIZE_FAIL);
                payment.setErrorCode(failure.errorCode());
                payment.setErrorDescription(failure.errorDescription());
            }
            case PaymentResult.Success success -> {
                log.warn("Invalid state");
                return null;
            }
        }

        payment = paymentRepository.save(payment);
        orderRepository.save(order);

        eventPublisher.publish(EventAggregateType.ORDER, payment.getId(), "PAYMENT_CREATED",
                Map.of("orderId", order.getId().toString(),
                        "paymentId", payment.getId().toString(),
                        "merchantId", merchantId.toString(),
                        "paymentStatus", payment.getStatus().name(),
                        "amountUnits", order.getAmount().getAmountUnits(),
                        "amountCurrency", order.getAmount().getCurrency(),
                        "paymentMethod", payment.getMethod()
                )
        );

        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional
    public PaymentResponse capture(UUID merchantId, UUID paymentId) {
//        Payment payment = paymentRepository.findByIdAndMerchantId(paymentId, merchantId).
//                orElseThrow(() -> new ResourceNotFoundException("Payment", paymentId));

        Payment payment = paymentRepository.findByIdAndMerchantIdForUpdate(paymentId, merchantId).
                orElseThrow(() -> new ResourceNotFoundException("Payment", paymentId));

        paymentTransitionService.apply(payment,PaymentEvent.CAPTURE_REQUEST);

        PaymentResult paymentResult = paymentGatewayRouter.capture(payment.getMethod(), paymentId);

        if (paymentResult instanceof PaymentResult.Success success){
            paymentTransitionService.apply(payment, PaymentEvent.CAPTURE_SUCCESS);
            payment.setCapturedAt(LocalDateTime.now());
            log.info("Payment captured, paymentId {}", paymentId);
        } else if (paymentResult instanceof PaymentResult.Failure failure){
            paymentTransitionService.apply(payment, PaymentEvent.CAPTURE_FAIL);
            payment.setErrorCode(failure.errorCode());
            payment.setErrorDescription(failure.errorDescription());
            log.warn("Payment capture failed, paymentId {}", paymentId);
        }

        paymentRepository.save(payment);

        eventPublisher.publish(EventAggregateType.ORDER, payment.getId(), "PAYMENT_STATUS_CHANGED",
                Map.of("orderId", payment.getOrder().getId().toString(),
                        "paymentId", payment.getId().toString(),
                        "merchantId", merchantId.toString(),
                        "paymentStatus", payment.getStatus().name(),
                        "amountUnits", payment.getAmount().getAmountUnits(),
                        "amountCurrency", payment.getAmount().getCurrency(),
                        "paymentMethod", payment.getMethod()
                )
        );

        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional
    public void resolveAuthorization(UUID paymentId, boolean approve, String bankRef, String errorCode, String errorDescription) {
//        Payment payment = paymentRepository.findById(paymentId)
//                .orElseThrow(() -> new ResourceNotFoundException("Payment", paymentId));

        Payment payment = paymentRepository.findByIdForUpdate(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", paymentId));

        if (payment.getStatus() != PaymentStatus.AUTHORIZING){
            log.warn("Payment is in authorizing state, paymentId: {}, status: {}", paymentId, payment.getStatus());
            return;
        }

        OrderRecord orderRecord = payment.getOrder();

        if (approve){
            paymentTransitionService.apply(payment, PaymentEvent.AUTHORIZE_SUCCESS);
            payment.setBankReference(bankRef);
            payment.setAuthorizedAt(LocalDateTime.now());

            paymentTransitionService.apply(payment, PaymentEvent.CAPTURE_REQUEST);
            PaymentResult captureResult = paymentGatewayRouter.capture(payment.getMethod(), paymentId);
            if (captureResult instanceof PaymentResult.Success success) {
                paymentTransitionService.apply(payment, PaymentEvent.CAPTURE_SUCCESS);
                payment.setCapturedAt (LocalDateTime.now());
                orderRecord.setOrderStatus (OrderStatus.PAID);
            } else if (captureResult instanceof PaymentResult.Failure failure){
                paymentTransitionService.apply(payment, PaymentEvent.CAPTURE_FAIL);
                payment.setErrorCode (failure.errorCode());
                payment.setErrorDescription (failure.errorDescription());
            }
        } else {
            paymentTransitionService.apply(payment, PaymentEvent.AUTHORIZE_FAIL);
            payment.setErrorCode (errorCode);
            payment.setErrorDescription (errorDescription);
        }

        paymentRepository.save(payment);
        orderRepository.save(orderRecord);

        eventPublisher.publish(EventAggregateType.ORDER, payment.getId(), "PAYMENT_STATUS_CHANGED",
                Map.of("orderId", payment.getOrder().getId().toString(),
                        "paymentId", payment.getId().toString(),
                        "merchantId", payment.getMerchantId().toString(),
                        "paymentStatus", payment.getStatus().name(),
                        "amountUnits", payment.getAmount().getAmountUnits(),
                        "amountCurrency", payment.getAmount().getCurrency(),
                        "paymentMethod", payment.getMethod()
                )
        );
    }
}
