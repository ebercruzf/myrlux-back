package com.ebercruz.myrluxb.deepseek.api.service.impl;

import com.ebercruz.myrluxb.deepseek.api.model.ChatMessage;
import com.ebercruz.myrluxb.deepseek.api.model.LLMRequest;
import com.ebercruz.myrluxb.deepseek.api.service.ConversationService;
import com.ebercruz.myrluxb.deepseek.api.service.LlmService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class LlmServiceImpl implements LlmService {
    private static final Logger logger = LoggerFactory.getLogger(LlmServiceImpl.class);
    private final WebClient webClient;
    private final String ollamaUrl;
    private final String modelName;
    private final ObjectMapper objectMapper;
    private final ConversationService conversationService;  // Cambiado a la interfaz

    public LlmServiceImpl(
            @Value("${llm.ollama.url:ws://localhost:11434}") String ollamaUrl,
            @Value("${llm.model.name:deepseek-r1:8b}") String modelName,
            ConversationService conversationService) {  // Cambiado a la interfaz
        this.ollamaUrl = ollamaUrl.replace("ws://", "http://");
        this.modelName = modelName;
        this.objectMapper = new ObjectMapper();
        this.conversationService = conversationService;
        this.webClient = WebClient.builder()
                .baseUrl(this.ollamaUrl)
                .build();
    }

    @Override
    public Flux<String> streamResponseRealTime(LLMRequest request) {
        logger.info("Initiating streaming request for prompt: {}, conversationId: {}",
                request.getPrompt(), request.getConversationId());

        if (Boolean.TRUE.equals(request.getUseContext()) && request.getConversationId() != null) {
            conversationService.addUserMessage(request.getConversationId(), request.getPrompt());
        }

        return webClient.post()
                .uri("/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createRequestPayload(request))
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(line -> {
                    try {
                        JsonNode node = objectMapper.readTree(line);
                        String response = node.path("response").asText("");
                        if (!response.isEmpty() && Boolean.TRUE.equals(request.getUseContext())) {
                            conversationService.addAssistantMessage(request.getConversationId(), response);
                        }
                        return response.isEmpty() ? Flux.empty() : Flux.just(response);
                    } catch (JsonProcessingException e) {
                        logger.error("Error processing response: {}", e.getMessage());
                        return Flux.empty();
                    }
                })
                .doOnNext(response -> logger.debug("Streaming response chunk: {}", response))
                .doOnError(error -> logger.error("Stream error: {}", error.getMessage()))
                .onErrorResume(error -> handleStreamError(error));
    }

    @Override
    public Mono<List<ChatMessage>> getConversationHistory(UUID conversationId) {
        return Mono.just(conversationService.getConversationHistory(conversationId));
    }

    @Override
    public Mono<Void> clearConversationHistory(UUID conversationId) {
        return Mono.fromRunnable(() -> conversationService.clearConversation(conversationId));
    }

    private String createRequestPayload(LLMRequest request) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("model", modelName);
            payload.put("prompt", request.getPrompt());
            payload.put("stream", true);
            payload.put("temperature", request.getTemperature());

            // Agregar parámetros adicionales si están presentes
            if (request.getExpectedResponseLength() != null) {
                payload.put("max_tokens", request.getExpectedResponseLength());
            }

            // Si hay system prompt, agregarlo al contexto
            if (request.getSystemPrompt() != null && !request.getSystemPrompt().isEmpty()) {
                payload.put("system", request.getSystemPrompt());
            }

            String jsonPayload = objectMapper.writeValueAsString(payload);
            logger.debug("Generated request payload: {}", jsonPayload);
            return jsonPayload;
        } catch (JsonProcessingException e) {
            logger.error("Error creating payload: {}", e.getMessage());
            throw new RuntimeException("Error creating request payload", e);
        }
    }

    // Método auxiliar para manejar errores de streaming
    private Flux<String> handleStreamError(Throwable error) {
        String errorMessage = String.format("Error en streaming: %s", error.getMessage());
        logger.error(errorMessage);
        return Flux.just(errorMessage);
    }
}