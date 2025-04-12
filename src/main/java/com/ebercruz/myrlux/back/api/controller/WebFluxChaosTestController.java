package com.ebercruz.myrlux.back.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador que simula caos (fallos, latencia) para probar patrones de resiliencia
 */
@RestController
@RequestMapping("/chaos-test")
public class WebFluxChaosTestController {
    private static final Logger log = LoggerFactory.getLogger(WebFluxChaosTestController.class);

    private final AtomicInteger counter = new AtomicInteger(0);

    /**
     * Simula latencia en la respuesta
     * @param delayMs tiempo de retraso en milisegundos
     * @return respuesta con retraso
     */
    @GetMapping("/latency")
    public Mono<String> simulateLatency(@RequestParam(defaultValue = "0") int delayMs) {
        int currentCount = counter.incrementAndGet();
        log.info("Petición #{} - Simulando latencia de {}ms", currentCount, delayMs);

        return Mono.just("Respuesta con latencia simulada de " + delayMs + "ms")
                .delayElement(Duration.ofMillis(delayMs))
                .doOnSuccess(s -> log.info("Petición #{} - Latencia completada", currentCount));
    }

    /**
     * Simula fallos según una tasa especificada
     * @param failureRate porcentaje de fallos (0-100)
     * @return respuesta exitosa o error
     */
    @GetMapping("/failure")
    public Mono<String> simulateFailure(@RequestParam(defaultValue = "0") int failureRate) {
        int current = counter.incrementAndGet();
        log.info("Petición #{} - Simulando fallo con tasa {}%", current, failureRate);

        // Fallar según la tasa especificada (0-100%)
        if (current % 100 <= failureRate) {
            log.warn("Petición #{} - Generando error simulado (tasa: {}%)", current, failureRate);
            return Mono.error(new RuntimeException("Error simulado para pruebas (tasa: " + failureRate + "%)"));
        }

        log.info("Petición #{} - Generando respuesta exitosa", current);
        return Mono.just("Respuesta exitosa (petición #" + current + ")");
    }

    /**
     * Simula una combinación de fallos y latencia
     * @param failureRate porcentaje de fallos (0-100)
     * @param delayMs tiempo de retraso en milisegundos
     * @return respuesta exitosa con retraso o error
     */
    @GetMapping("/mixed")
    public Mono<String> simulateMixed(
            @RequestParam(defaultValue = "0") int failureRate,
            @RequestParam(defaultValue = "0") int delayMs) {

        int current = counter.incrementAndGet();
        log.info("Petición #{} - Simulando escenario mixto (fallo: {}%, latencia: {}ms)",
                current, failureRate, delayMs);

        // Primero determinar si debe fallar
        if (current % 100 <= failureRate) {
            log.warn("Petición #{} - Generando error simulado en escenario mixto", current);
            return Mono.error(new RuntimeException("Error simulado para pruebas mixtas"));
        }

        // Si no falla, puede tener latencia
        log.info("Petición #{} - Generando respuesta con latencia en escenario mixto", current);
        return Mono.just("Respuesta mixta exitosa con latencia de " + delayMs + "ms (petición #" + current + ")")
                .delayElement(Duration.ofMillis(delayMs))
                .doOnSuccess(s -> log.info("Petición #{} - Respuesta mixta completada", current));
    }

    /**
     * Reinicia el contador interno de peticiones
     * @return confirmación de reinicio
     */
    @GetMapping("/reset")
    public Mono<String> resetCounter() {
        int previous = counter.getAndSet(0);
        log.info("Contador reiniciado. Valor anterior: {}", previous);
        return Mono.just("Contador reiniciado. Valor anterior: " + previous);
    }
}