package com.ebercruz.myrlux.back.api.service.impl;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ResilienceTestService {
    private static final Logger log = LoggerFactory.getLogger(ResilienceTestService.class);

    private final WebClient webClient;
    private final AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    public ResilienceTestService(WebClient.Builder webClientBuilder) {
        // Asumimos que el servicio de caos está en el mismo servidor
        this.webClient = webClientBuilder.baseUrl("http://localhost:11002").build();
    }

    // TEST DE CIRCUIT BREAKER
    @CircuitBreaker(name = "testService", fallbackMethod = "fallbackForFailure")
    public Mono<Map<String, Object>> testCircuitBreaker(boolean shouldFail) {
        int count = counter.incrementAndGet();
        log.info("Test CircuitBreaker #{} - shouldFail: {}", count, shouldFail);

        return webClient.get()
                .uri("/chaos-test/failure?failureRate={rate}", shouldFail ? 100 : 0)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("success", true);
                    result.put("data", response);
                    result.put("count", count);
                    result.put("circuitState", "CLOSED"); // En un caso real se podría obtener del estado actual
                    return result;
                });
    }

    // TEST DE BULKHEAD
    @Bulkhead(name = "testService", fallbackMethod = "fallbackForBulkhead")
    public Mono<Map<String, Object>> testBulkhead() {
        int count = counter.incrementAndGet();
        log.info("Test Bulkhead #{}", count);

        return webClient.get()
                .uri("/chaos-test/latency?delayMs=500")
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("success", true);
                    result.put("data", response);
                    result.put("count", count);
                    return result;
                });
    }

    // TEST DE RETRY
    @Retry(name = "testService", fallbackMethod = "fallbackForRetry")
    public Mono<Map<String, Object>> testRetry() {
        int count = counter.incrementAndGet();
        int failRate = 75; // Alta probabilidad de fallo para probar el retry
        log.info("Test Retry #{} - failRate: {}", count, failRate);

        return webClient.get()
                .uri("/chaos-test/failure?failureRate={rate}", failRate)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("success", true);
                    result.put("data", response);
                    result.put("count", count);
                    return result;
                });
    }

    // TEST DE TIME LIMITER
    @TimeLimiter(name = "testService", fallbackMethod = "fallbackForTimeout")
    public Mono<Map<String, Object>> testTimeLimiter(int delayMs) {
        int count = counter.incrementAndGet();
        log.info("Test TimeLimiter #{} - delay: {}ms", count, delayMs);

        return webClient.get()
                .uri("/chaos-test/latency?delayMs={delay}", delayMs)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("success", true);
                    result.put("data", response);
                    result.put("count", count);
                    result.put("delay", delayMs);
                    return result;
                });
    }

    // TEST COMPLETO
    @CircuitBreaker(name = "testService", fallbackMethod = "fallbackForMixedTest")
    @Bulkhead(name = "testService")
    @Retry(name = "testService")
    @TimeLimiter(name = "testService")
    public Mono<Map<String, Object>> testAllPatterns(int failureRate, int delayMs) {
        int count = counter.incrementAndGet();
        log.info("Test Completo #{} - failRate: {}%, delay: {}ms", count, failureRate, delayMs);

        return webClient.get()
                .uri("/chaos-test/mixed?failureRate={rate}&delayMs={delay}", failureRate, delayMs)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("success", true);
                    result.put("data", response);
                    result.put("count", count);
                    result.put("failureRate", failureRate);
                    result.put("delayMs", delayMs);
                    result.put("patternsApplied", "Circuit Breaker, Bulkhead, Retry, TimeLimiter");
                    return result;
                });
    }

    // MÉTODOS DE FALLBACK

    public Mono<Map<String, Object>> fallbackForFailure(boolean shouldFail, Exception ex) {
        log.warn("Fallback para Circuit Breaker activado. Error: {}", ex.getMessage());
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("fallbackActivated", true);
        result.put("pattern", "Circuit Breaker");
        result.put("error", ex.getMessage());
        result.put("shouldFail", shouldFail);
        return Mono.just(result);
    }

    public Mono<Map<String, Object>> fallbackForBulkhead(Exception ex) {
        log.warn("Fallback para Bulkhead activado. Error: {}", ex.getMessage());
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("fallbackActivated", true);
        result.put("pattern", "Bulkhead");
        result.put("error", ex.getMessage());
        return Mono.just(result);
    }

    public Mono<Map<String, Object>> fallbackForRetry(Exception ex) {
        log.warn("Fallback para Retry activado. Error: {}", ex.getMessage());
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("fallbackActivated", true);
        result.put("pattern", "Retry");
        result.put("error", ex.getMessage());
        return Mono.just(result);
    }

    public Mono<Map<String, Object>> fallbackForTimeout(int delayMs, Exception ex) {
        log.warn("Fallback para TimeLimiter activado. Delay: {}ms. Error: {}", delayMs, ex.getMessage());
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("fallbackActivated", true);
        result.put("pattern", "TimeLimiter");
        result.put("requestedDelay", delayMs);
        result.put("error", ex.getMessage());
        return Mono.just(result);
    }

    public Mono<Map<String, Object>> fallbackForMixedTest(int failureRate, int delayMs, Exception ex) {
        log.warn("Fallback para test completo activado. Fallo: {}%, Delay: {}ms. Error: {}",
                failureRate, delayMs, ex.getMessage());
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("fallbackActivated", true);
        result.put("pattern", "Combinación de patrones");
        result.put("failureRate", failureRate);
        result.put("delayMs", delayMs);
        result.put("error", ex.getMessage());
        return Mono.just(result);
    }

    // Método para resetear contadores
    public Mono<String> resetCounters() {
        counter.set(0);
        return webClient.get()
                .uri("/chaos-test/reset")
                .retrieve()
                .bodyToMono(String.class);
    }
}