package com.dineshK.Razorpay.merchant.service.impl;

import com.dineshK.Razorpay.common.enums.MerchantStatus;
import com.dineshK.Razorpay.common.enums.UserRole;
import com.dineshK.Razorpay.common.exception.DuplicateResourceException;
import com.dineshK.Razorpay.common.exception.ResourceNotFoundException;
import com.dineshK.Razorpay.merchant.Mapper.MerchantMapper;
import com.dineshK.Razorpay.merchant.dto.request.LoginRequest;
import com.dineshK.Razorpay.merchant.dto.request.MerchantSignupRequest;
import com.dineshK.Razorpay.merchant.dto.response.LoginResponse;
import com.dineshK.Razorpay.merchant.dto.response.MerchantResponse;
import com.dineshK.Razorpay.merchant.entity.AppUser;
import com.dineshK.Razorpay.merchant.entity.Merchant;
import com.dineshK.Razorpay.merchant.repository.AppUserRepository;
import com.dineshK.Razorpay.merchant.repository.MerchantRepository;
import com.dineshK.Razorpay.merchant.security.JwtUtil;
import com.dineshK.Razorpay.merchant.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j // helps in adding logger statement throughout the code
public class AuthServiceImpl implements AuthService {

    private final MerchantRepository merchantRepository;
    private final AppUserRepository appUserRepository;
    private final MerchantMapper merchantMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public MerchantResponse signup(MerchantSignupRequest request){
        if (merchantRepository.existsByEmail(request.email())){
            throw new DuplicateResourceException("DUPLICATE_MERCHANT_EMAIL",
                    "Merchant with the given email already exsists: "+request.email());
        }

        Merchant merchant = merchantMapper.toEntitySignUpRequest(request);
        merchant.setStatus(MerchantStatus.PENDING_KYC);
        merchant = merchantRepository.save(merchant);

        AppUser appUser = AppUser.builder()
                .email(request.email())
                .merchant(merchant)
                .passwordHash(request.password())
                .role(UserRole.OWNER)
                .build();
        appUserRepository.save(appUser);

        return merchantMapper.toResponse(merchant);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        AppUser appUser = appUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.email()));
        String token = jwtUtil.generateAccessToken(request.email(), appUser.getMerchant().getId(), appUser.getRole().toString());
        return new LoginResponse(token);
    }

}
