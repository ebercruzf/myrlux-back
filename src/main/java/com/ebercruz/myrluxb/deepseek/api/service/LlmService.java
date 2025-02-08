package com.ebercruz.myrluxb.deepseek.api.service;

import com.ebercruz.myrluxb.deepseek.api.model.ChatMessage;
import com.ebercruz.myrluxb.deepseek.api.model.LLMRequest;

import reactor.core.publisher.Flux;

import com.ebercruz.myrluxb.deepseek.api.model.LLMRequest;
import com.ebercruz.myrluxb.deepseek.api.model.Conversation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface LlmService {
    /**
     * Procesa una solicitud en tiempo real con soporte para contexto de conversación
     * @param request La solicitud con el prompt y configuraciones
     * @return Un flujo de respuestas del modelo
     */
    Flux<String> streamResponseRealTime(LLMRequest request);

    /**
     * Obtiene el historial de una conversación específica
     * @param conversationId ID de la conversación
     * @return La lista de mensajes de la conversación
     */
    Mono<List<ChatMessage>> getConversationHistory(UUID conversationId);

    /**
     * Limpia el historial de una conversación
     * @param conversationId ID de la conversación a limpiar
     */
    Mono<Void> clearConversationHistory(UUID conversationId);
}
