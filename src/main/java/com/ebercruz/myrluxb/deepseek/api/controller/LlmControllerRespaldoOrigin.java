/*
package com.ebercruz.myrluxb.deepseek.api.controller;

import com.ebercruz.myrluxb.deepseek.api.model.LLMRequest;
import com.ebercruz.myrluxb.deepseek.api.model.ModelApiResponse;
import com.ebercruz.myrluxb.deepseek.api.service.LlmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController

@Tag(name = "LLM", description = "Operaciones con el modelo de lenguaje")
@Validated
public class LlmControllerRespaldoOrigin {

    private static final Logger LOGGER = LoggerFactory.getLogger(LlmControllerRespaldoOrigin.class);

    private final LlmService llmService;

    public LlmControllerRespaldoOrigin(LlmService llmService) {
        this.llmService = llmService;
    }

    @GetMapping("/homel")
    public Mono<String> homel() {
        LOGGER.info("Processing home APIController: {}");
        return Mono.just("Bienvenido a la aplicación MyrluxBack");
    }

    @Operation(
            operationId = "chat",
            summary = "Genera una respuesta de chat",
            description = "Endpoint unificado que maneja tanto streaming como respuestas completas",
            tags = { "LLM" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuesta exitosa", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ModelApiResponse.class)),
                            @Content(mediaType = "text/event-stream", schema = @Schema(implementation = ModelApiResponse.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
                    @ApiResponse(responseCode = "404", description = "No encontrado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @PostMapping(
            value = "/llm/chat",
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE },
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<ResponseEntity<ModelApiResponse>> chat(
            @Parameter(name = "LLMRequest", description = "Solicitud para el modelo de lenguaje", required = true)
            @Valid @RequestBody Mono<LLMRequest> llmRequest,
            ServerWebExchange exchange) {

        return llmRequest.flatMap(request -> {
            boolean useStreaming = exchange.getRequest()
                    .getHeaders()
                    .getAccept()
                    .stream()
                    .anyMatch(mediaType ->
                            mediaType.isCompatibleWith(MediaType.TEXT_EVENT_STREAM));

            if (useStreaming) {
                exchange.getResponse()
                        .getHeaders()
                        .setContentType(MediaType.TEXT_EVENT_STREAM);

                return llmService.streamResponse(request)
                        .map(ResponseEntity::ok)
                        .onErrorResume(e -> Mono.just(
                                ResponseEntity.internalServerError()
                                        .body(new ModelApiResponse()
                                                .message("Error en streaming: " + e.getMessage())
                                                .code("500")
                                                .success(false))
                        ));
            } else {
                return llmService.generateResponse(request)
                        .map(ResponseEntity::ok)
                        .onErrorResume(e -> Mono.just(
                                ResponseEntity.internalServerError()
                                        .body(new ModelApiResponse()
                                                .message("Error procesando solicitud: " + e.getMessage())
                                                .code("500")
                                                .success(false))
                        ));
            }
        });
    }
}*/
