package com.ebercruz.myrlux.back.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resultado de una prueba de resiliencia")
public class TestResult {

    @Schema(description = "Tipo de prueba realizada")
    private final String testType;

    @Schema(description = "Número total de llamadas realizadas")
    private final int totalCalls;

    @Schema(description = "Número de llamadas exitosas")
    private final int successfulCalls;

    @Schema(description = "Número de llamadas fallidas")
    private final int failedCalls;

    @Schema(description = "Número de veces que se activó el fallback")
    private final int fallbackActivations;

    @Schema(description = "Tasa de fallo configurada (0-100%)")
    private final int configuredFailureRate;

    @Schema(description = "Latencia configurada en ms")
    private final int configuredLatencyMs;

    @Schema(description = "Tasa de éxito de la prueba (0-100%)")
    private final double successRate;

    @Schema(description = "Marca de tiempo de cuando finalizó la prueba")
    private final LocalDateTime timestamp;

    public TestResult(String testType, int totalCalls, int successfulCalls, int failedCalls,
                      int fallbackActivations, int configuredFailureRate, int configuredLatencyMs,
                      double successRate) {
        this.testType = testType;
        this.totalCalls = totalCalls;
        this.successfulCalls = successfulCalls;
        this.failedCalls = failedCalls;
        this.fallbackActivations = fallbackActivations;
        this.configuredFailureRate = configuredFailureRate;
        this.configuredLatencyMs = configuredLatencyMs;
        this.successRate = successRate;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getTestType() {
        return testType;
    }

    public int getTotalCalls() {
        return totalCalls;
    }

    public int getSuccessfulCalls() {
        return successfulCalls;
    }

    public int getFailedCalls() {
        return failedCalls;
    }

    public int getFallbackActivations() {
        return fallbackActivations;
    }

    public int getConfiguredFailureRate() {
        return configuredFailureRate;
    }

    public int getConfiguredLatencyMs() {
        return configuredLatencyMs;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "testType='" + testType + '\'' +
                ", totalCalls=" + totalCalls +
                ", successfulCalls=" + successfulCalls +
                ", failedCalls=" + failedCalls +
                ", fallbackActivations=" + fallbackActivations +
                ", configuredFailureRate=" + configuredFailureRate +
                ", configuredLatencyMs=" + configuredLatencyMs +
                ", successRate=" + successRate +
                ", timestamp=" + timestamp +
                '}';
    }
}