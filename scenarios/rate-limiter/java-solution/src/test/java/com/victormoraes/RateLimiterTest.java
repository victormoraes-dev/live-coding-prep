package com.victormoraes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RateLimiterTest {

    RateLimiter rateLimiter;

    @BeforeEach
    void init() {
        rateLimiter = new RateLimiter(2, 1000);
    }

    // Scenario 1: maxRequests=2, windowSize=1000ms
    // allow("alice") x3 in rapid succession → true, true, false
    @Test
    public void first2RequestsAllowedThirdExceeds() throws InterruptedException {
        Assertions.assertTrue(rateLimiter.allowRequest("alice"));
        Assertions.assertTrue(rateLimiter.allowRequest("alice"));
        Assertions.assertFalse(rateLimiter.allowRequest("alice"));
    }

    // Scenario 2: maxRequests=2, windowSize=1000ms
    // T=0 → true, T=1100ms → true (first request expired), T=1200ms → true
    @Test
    public void windowSlidesAfterExpiryNewSlotOpens() throws InterruptedException {
        Assertions.assertTrue(rateLimiter.allowRequest("alice"));
        Thread.sleep(1100);
        Assertions.assertTrue(rateLimiter.allowRequest("alice"));
        Assertions.assertTrue(rateLimiter.allowRequest("alice"));
    }

    // Scenario 3: maxRequests=5, windowSize=1000ms
    // 6 rapid requests from "bob" → true(×5), false
    @Test
    public void fiveAllowedSixthBlocked() {
        RateLimiter limiter = new RateLimiter(5, 1000);
        for (int i = 0; i < 5; i++) {
            Assertions.assertTrue(limiter.allowRequest("bob"), "Request " + (i + 1) + " should be allowed");
        }
        Assertions.assertFalse(limiter.allowRequest("bob"), "6th request should be blocked");
    }

    // Scenario 4: maxRequests=2, windowSize=1000ms
    // Per-user tracking: alice fills both slots, bob is still allowed
    // (independent), alice is then blocked
    @Test
    public void perUserTrackingIsIndependent() {
        Assertions.assertTrue(rateLimiter.allowRequest("alice"));
        Assertions.assertTrue(rateLimiter.allowRequest("alice"));
        Assertions.assertTrue(rateLimiter.allowRequest("bob"));
        Assertions.assertFalse(rateLimiter.allowRequest("alice"));
    }

    // Scenario 5: maxRequests=3, windowSize=5000ms
    // 3 requests from "mallory" at T=0 → true(×3), wait 5s, 1 more → true
    @Test
    public void windowFullySlidesAllSlotsAvailableAgain() throws InterruptedException {
        RateLimiter limiter = new RateLimiter(3, 5000);
        Assertions.assertTrue(limiter.allowRequest("mallory"));
        Assertions.assertTrue(limiter.allowRequest("mallory"));
        Assertions.assertTrue(limiter.allowRequest("mallory"));
        Thread.sleep(5001);
        Assertions.assertTrue(limiter.allowRequest("mallory"));
    }
}
