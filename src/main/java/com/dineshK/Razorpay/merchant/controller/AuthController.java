package com.dineshK.Razorpay.merchant.controller;

import com.dineshK.Razorpay.merchant.dto.request.LoginRequest;
import com.dineshK.Razorpay.merchant.dto.request.MerchantSignupRequest;
import com.dineshK.Razorpay.merchant.dto.response.LoginResponse;
import com.dineshK.Razorpay.merchant.dto.response.MerchantResponse;
import com.dineshK.Razorpay.merchant.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MerchantResponse> signup(@RequestBody @Valid MerchantSignupRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(request));
    }
}
