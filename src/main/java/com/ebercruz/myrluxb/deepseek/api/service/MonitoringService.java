package com.ebercruz.myrluxb.deepseek.api.service;

import com.ebercruz.myrluxb.deepseek.api.model.*;
import reactor.core.publisher.Mono;

public interface MonitoringService {
    Mono<MetricsResponse> getMetrics();
    Mono<String> getPrometheusMetrics();
    Mono<HealthResponse> checkHealth();
    Mono<LivenessResponse> checkLiveness();
    Mono<ReadinessResponse> checkReadiness();
}