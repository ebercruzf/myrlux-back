package com.ebercruz.myrluxb.deepseek.api.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientConfig.class);

    @Bean
    public WebClient ollamaWebClient(
            @Value("${llm.ollama.url:http://localhost:11434}") String ollamaUrl) {  // ${llm.ollama.url:http://localhost:11434}
        return WebClient.builder()
                .baseUrl(ollamaUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .filter(ExchangeFilterFunction.ofRequestProcessor(
                        clientRequest -> {
                            LOGGER.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
                            return Mono.just(clientRequest);
                        }
                ))
                .filter(ExchangeFilterFunction.ofResponseProcessor(
                        clientResponse -> {
                            LOGGER.debug("Response status: {}", clientResponse.statusCode());
                            return Mono.just(clientResponse);
                        }
                ))
                .build();
    }
}