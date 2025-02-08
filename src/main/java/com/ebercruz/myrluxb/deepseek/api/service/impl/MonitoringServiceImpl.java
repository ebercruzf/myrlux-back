package com.ebercruz.myrluxb.deepseek.api.service.impl;

import com.ebercruz.myrluxb.deepseek.api.model.*;
import com.ebercruz.myrluxb.deepseek.api.service.MonitoringService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MonitoringServiceImpl implements MonitoringService {
    private static final Logger log = LoggerFactory.getLogger(MonitoringServiceImpl.class);
    private final WebClient ollamaWebClient;
    private final ObjectMapper objectMapper;
    private final Runtime runtime = Runtime.getRuntime();

    public MonitoringServiceImpl(
            WebClient ollamaWebClient,
            ObjectMapper objectMapper) {
        this.ollamaWebClient = ollamaWebClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<MetricsResponse> getMetrics() {
        return Mono.fromCallable(() -> {
            List<MetricsResponseMeasurementsInner> measurements = new ArrayList<>();
            measurements.add(createSystemMetrics());
            measurements.add(createOllamaMetrics());
            measurements.add(createApiMetrics());
            return new MetricsResponse(measurements);
        });
    }

    @Override
    public Mono<String> getPrometheusMetrics() {
        return getMetrics()
                .map(this::convertToPrometheusFormat);
    }

    @Override
    public Mono<HealthResponse> checkHealth() {
        return checkOllamaHealth()
                .map(ollamaStatus -> {
                    HealthResponseComponents components = new HealthResponseComponents();

                    // Componente Ollama
                    ComponentHealth ollamaHealth = new ComponentHealth()
                            .status(ollamaStatus ? ComponentHealth.StatusEnum.UP : ComponentHealth.StatusEnum.DOWN)
                            .details(Map.of(
                                    "timestamp", Instant.now().toString(),
                                    "lastCheck", Instant.now().toString()
                            ));
                    components.setOllama(ollamaHealth);

                    return new HealthResponse()
                            .status(ollamaStatus ? HealthResponse.StatusEnum.UP : HealthResponse.StatusEnum.DOWN)
                            .components(components);
                });
    }

    @Override
    public Mono<LivenessResponse> checkLiveness() {
        return Mono.just(new LivenessResponse()
                .status(LivenessResponse.StatusEnum.UP)
                .timestamp(Instant.now().atOffset(ZoneOffset.UTC)));  // Convertimos a OffsetDateTime
    }

    @Override
    public Mono<ReadinessResponse> checkReadiness() {
        return checkOllamaHealth()
                .map(ollamaStatus -> {
                    ReadinessResponseDependencies dependencies = new ReadinessResponseDependencies()
                            .ollama(ollamaStatus ? ReadinessResponseDependencies.OllamaEnum.UP : ReadinessResponseDependencies.OllamaEnum.DOWN);

                    return new ReadinessResponse()
                            .status(ollamaStatus ? ReadinessResponse.StatusEnum.UP : ReadinessResponse.StatusEnum.DOWN)
                            .dependencies(dependencies);
                });
    }

    private MetricsResponseMeasurementsInner createSystemMetrics() {
        List<MetricsResponseMeasurementsInnerMeasurementsInner> measurements = new ArrayList<>();

        measurements.add(new MetricsResponseMeasurementsInnerMeasurementsInner()
                .statistic("memory.total")
                .value(BigDecimal.valueOf(runtime.totalMemory())
                        .divide(BigDecimal.valueOf(1024 * 1024), 2, RoundingMode.HALF_UP)));

        measurements.add(new MetricsResponseMeasurementsInnerMeasurementsInner()
                .statistic("memory.free")
                .value(BigDecimal.valueOf(runtime.freeMemory())
                        .divide(BigDecimal.valueOf(1024 * 1024), 2, RoundingMode.HALF_UP)));

        measurements.add(new MetricsResponseMeasurementsInnerMeasurementsInner()
                .statistic("processors")
                .value(BigDecimal.valueOf(runtime.availableProcessors())));

        return new MetricsResponseMeasurementsInner()
                .name("system")
                .measurements(measurements);
    }

    private MetricsResponseMeasurementsInner createOllamaMetrics() {
        List<MetricsResponseMeasurementsInnerMeasurementsInner> measurements = new ArrayList<>();

        measurements.add(new MetricsResponseMeasurementsInnerMeasurementsInner()
                .statistic("status")
                .value(BigDecimal.ONE));

        measurements.add(new MetricsResponseMeasurementsInnerMeasurementsInner()
                .statistic("response_time")
                .value(BigDecimal.ZERO));

        return new MetricsResponseMeasurementsInner()
                .name("ollama")
                .measurements(measurements);
    }

    private MetricsResponseMeasurementsInner createApiMetrics() {
        List<MetricsResponseMeasurementsInnerMeasurementsInner> measurements = new ArrayList<>();

        measurements.add(new MetricsResponseMeasurementsInnerMeasurementsInner()
                .statistic("uptime")
                .value(BigDecimal.valueOf(Duration.ofMillis(System.currentTimeMillis()).getSeconds())));

        measurements.add(new MetricsResponseMeasurementsInnerMeasurementsInner()
                .statistic("thread_count")
                .value(BigDecimal.valueOf(Thread.activeCount())));

        return new MetricsResponseMeasurementsInner()
                .name("api")
                .measurements(measurements);
    }

    private Mono<Boolean> checkOllamaHealth() {
        return ollamaWebClient
                .get()
                .uri("/api/tags")
                .retrieve()
                .onStatus(
                        status -> status.value() >= 500,
                        response -> Mono.error(new RuntimeException("Ollama server error"))
                )
                .bodyToMono(String.class)
                .map(response -> true)
                .onErrorResume(ex -> {
                    log.error("Error checking Ollama health: {}", ex.getMessage());
                    return Mono.just(false);
                })
                .timeout(Duration.ofSeconds(5))
                .retry(3);
    }

    private String convertToPrometheusFormat(MetricsResponse metrics) {
        StringBuilder prometheus = new StringBuilder();
        for (MetricsResponseMeasurementsInner measurement : metrics.getMeasurements()) {
            String name = measurement.getName();
            for (MetricsResponseMeasurementsInnerMeasurementsInner metric : measurement.getMeasurements()) {
                prometheus.append(String.format(
                        "%s_%s{} %s\n",
                        name,
                        metric.getStatistic().replace('.', '_'),
                        metric.getValue().toString()
                ));
            }
        }
        return prometheus.toString();
    }
}