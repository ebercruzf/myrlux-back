package com.ebercruz.myrluxb.deepseek.api.controller;

import com.ebercruz.myrluxb.deepseek.api.contract.MonitoringApi;
import com.ebercruz.myrluxb.deepseek.api.model.*;
import com.ebercruz.myrluxb.deepseek.api.service.MonitoringService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class MonitoringController implements MonitoringApi {

    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @Override
    public Mono<ResponseEntity<MetricsResponse>> getMetrics(ServerWebExchange exchange) {
        return monitoringService.getMetrics().map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<String>> getPrometheusMetrics(ServerWebExchange exchange) {
        return monitoringService.getPrometheusMetrics().map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<HealthResponse>> healthCheck(ServerWebExchange exchange) {
        return monitoringService.checkHealth().map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<LivenessResponse>> livenessCheck(ServerWebExchange exchange) {
        return monitoringService.checkLiveness().map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<ReadinessResponse>> readinessCheck(ServerWebExchange exchange) {
        return monitoringService.checkReadiness().map(ResponseEntity::ok);
    }
}