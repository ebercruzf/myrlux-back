/*
package com.ebercruz.myrluxb.deepseek.api.service.impl;

import com.ebercruz.myrluxb.deepseek.api.model.LLMRequest;
import com.ebercruz.myrluxb.deepseek.api.model.ModelApiResponse;
import com.ebercruz.myrluxb.deepseek.api.model.OllamaRequest;
import com.ebercruz.myrluxb.deepseek.api.service.LlmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class LlmServiceImplCopy implements LlmService {
    private static final Logger logger = LoggerFactory.getLogger(LlmServiceImplCopy.class);
    private final WebClient ollamaClient;
    private final String modelName;

    public LlmServiceImplCopy(
            WebClient.Builder webClientBuilder,
            @Value("${llm.ollama.url:http://localhost:11434}") String ollamaUrl,
            @Value("${llm.model.name:deepseek-r1:8b}") String modelName) {
        this.ollamaClient = webClientBuilder.baseUrl(ollamaUrl).build();
        this.modelName = modelName;
    }

    @Override
    public Mono<ModelApiResponse> generateResponse(LLMRequest request) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", modelName);
        requestBody.put("prompt", request.getPrompt());
        requestBody.put("stream", false);
        requestBody.put("temperature", request.getTemperature() != null ? request.getTemperature() : 0.7);

        return ollamaClient.post()
                .uri("/api/generate")
                .bodyValue(requestBody)  // Usando Map en lugar de OllamaRequest
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> new ModelApiResponse()
                        .message(response)
                        .code("200")
                        .success(true));
    }

    @Override
    public Mono<ModelApiResponse> streamResponse(LLMRequest request) {
        return ollamaClient.post()
                .uri("/api/generate")
                .bodyValue(createOllamaRequest(request, true))
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .map(responses -> new ModelApiResponse()
                        .message(String.join("", responses))
                        .code("200")
                        .success(true));
    }

    private OllamaRequest createOllamaRequest(LLMRequest request, boolean stream) {
        return new OllamaRequest(
                modelName,
                request.getPrompt(),
                stream,
                request.getTemperature() != null ? request.getTemperature() : 0.7
        );
    }
}*/
