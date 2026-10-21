package com.dineshK.Razorpay.common.ratelimit;

public record RateLimitResult(boolean isAllowed, int remaining, int retryAfterSeconds) {

    public static RateLimitResult allowed(int remaining){
        return new RateLimitResult(true, remaining, 0);
    }

    public static RateLimitResult declined(int retryAfterSeconds){
        return new RateLimitResult(false, 0, retryAfterSeconds);
    }
}
