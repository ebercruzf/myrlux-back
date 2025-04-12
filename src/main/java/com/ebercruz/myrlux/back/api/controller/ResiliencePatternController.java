package com.ebercruz.myrlux.back.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import com.ebercruz.myrlux.back.api.service.impl.ResilienceTestService;

import java.util.Map;

@RestController
@RequestMapping("/api/resilience")
public class ResiliencePatternController {

    private final ResilienceTestService resilienceTestService;

    @Autowired
    public ResiliencePatternController(ResilienceTestService resilienceTestService) {
        this.resilienceTestService = resilienceTestService;
    }

    @GetMapping("/circuit-breaker")
    public Mono<Map<String, Object>> testCircuitBreaker(
            @RequestParam(defaultValue = "false") boolean shouldFail) {
        return resilienceTestService.testCircuitBreaker(shouldFail);
    }

    @GetMapping("/bulkhead")
    public Mono<Map<String, Object>> testBulkhead() {
        return resilienceTestService.testBulkhead();
    }

    @GetMapping("/retry")
    public Mono<Map<String, Object>> testRetry() {
        return resilienceTestService.testRetry();
    }

    @GetMapping("/time-limiter")
    public Mono<Map<String, Object>> testTimeLimiter(
            @RequestParam(defaultValue = "3000") int delayMs) {
        return resilienceTestService.testTimeLimiter(delayMs);
    }

    @GetMapping("/all-patterns")
    public Mono<Map<String, Object>> testAllPatterns(
            @RequestParam(defaultValue = "50") int failureRate,
            @RequestParam(defaultValue = "1500") int delayMs) {
        return resilienceTestService.testAllPatterns(failureRate, delayMs);
    }

    @PostMapping("/reset")
    public Mono<String> resetCounters() {
        return resilienceTestService.resetCounters();
    }
}