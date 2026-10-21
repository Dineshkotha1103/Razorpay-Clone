package com.dineshK.Razorpay.merchant.service.impl;

import com.dineshK.Razorpay.common.exception.ResourceNotFoundException;
import com.dineshK.Razorpay.merchant.entity.Customer;
import com.dineshK.Razorpay.merchant.entity.Merchant;
import com.dineshK.Razorpay.merchant.repository.CustomerRepository;
import com.dineshK.Razorpay.merchant.repository.MerchantRepository;
import com.dineshK.Razorpay.merchant.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final MerchantRepository merchantRepository;

    @Override
    @Transactional
    public UUID findOrCreate(UUID merchantId, String email, String name, String phone) {

        if (email == null || email.isBlank()) return null;


        return customerRepository.findByMerchant_IdAndEmail(merchantId, email).
                map(Customer::getId).orElseGet(() -> createNew(merchantId, email, name, phone));
    }

    private UUID createNew(UUID merchantId, String email, String name, String phone){

        Merchant merchant = merchantRepository.findById(merchantId).
                orElseThrow(() -> new ResourceNotFoundException("Merchant", merchantId));

        Customer customer = Customer.builder()
                .merchant(merchant)
                .name(name)
                .email(email)
                .phone(phone)
                .build();

        customer = customerRepository.save(customer);
        log.info("Customer created via findOrCreate id={} merchantId={} email={}",
                customer.getId(), merchantId, email);
        return customer.getId();
    }
}
