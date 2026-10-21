package com.dineshK.Razorpay.common.exception;

import lombok.Getter;

@Getter
public class BusinessViolationException extends RuntimeException{
    private final String errorCode;

    public BusinessViolationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
