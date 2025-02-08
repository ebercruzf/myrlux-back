package com.ebercruz.myrluxb.deepseek.api.controller;

import com.ebercruz.myrluxb.deepseek.api.model.LLMRequest;
import com.ebercruz.myrluxb.deepseek.api.service.LlmService;
import com.ebercruz.myrluxb.deepseek.api.service.ConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@Tag(name = "LLM", description = "Operaciones con el modelo de lenguaje")
@RequestMapping("/llm")
public class LlmController {
    private static final Logger logger = LoggerFactory.getLogger(LlmController.class);
    private final LlmService llmService;
    private final ConversationService conversationService;

    public LlmController(LlmService llmService, ConversationService conversationService) {
        this.llmService = llmService;
        this.conversationService = conversationService;
    }

    @Operation(
            operationId = "chat",
            summary = "Genera una respuesta de chat en tiempo real con contexto",
            description = "Endpoint para streaming de respuestas del modelo de lenguaje manteniendo contexto de conversación",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Stream de respuestas del modelo",
                            content = @Content(
                                    mediaType = MediaType.TEXT_EVENT_STREAM_VALUE,
                                    schema = @Schema(type = "string")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Solicitud inválida"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor"
                    )
            }
    )
    @PostMapping(
            value = "/chat",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Flux<String> streamChat(
            @Parameter(description = "Solicitud para el modelo de lenguaje", required = true)
            @Valid @RequestBody LLMRequest request) {

        // Generar nuevo ID de conversación si no existe
        if (request.getConversationId() == null) {
            request.setConversationId(UUID.randomUUID());
        }

        logger.info("Iniciando streaming de chat para prompt: {}, conversationId: {}",
                request.getPrompt(), request.getConversationId());

        // Procesar el prompt con contexto si está habilitado
        if (Boolean.TRUE.equals(request.getUseContext())) {
            String contextualPrompt = conversationService.buildContextualPrompt(
                    request.getConversationId(),
                    request.getSystemPrompt(),
                    request.getPrompt()
            );
            request.setPrompt(contextualPrompt);
        }

        return llmService.streamResponseRealTime(request)
                .doOnNext(response -> {
                    // Guardar respuesta en el contexto de la conversación
                    if (Boolean.TRUE.equals(request.getUseContext())) {
                        conversationService.addAssistantMessage(
                                request.getConversationId(),
                                response
                        );
                    }
                    logger.debug("Enviando respuesta: {}", response);
                })
                .doOnError(error -> logger.error("Error en streaming: {}", error.getMessage()))
                .onErrorResume(error -> Flux.just("Error: " + error.getMessage()));
    }

    @Operation(
            operationId = "clearContext",
            summary = "Limpia el contexto de una conversación",
            description = "Elimina el historial de mensajes de una conversación específica"
    )
    @DeleteMapping("/conversations/{conversationId}")
    public void clearConversationContext(
            @PathVariable UUID conversationId) {
        logger.info("Limpiando contexto de conversación: {}", conversationId);
        conversationService.clearConversation(conversationId);
    }

    @PostConstruct
    private void init() {
        logger.info("LLM Controller initialized with endpoint mapped to: /api/llm/chat");
        logger.info("Base path configuration: {}", "/api");
        logger.info("Full endpoint URL: http://localhost:11004/api/llm/chat");
    }
}