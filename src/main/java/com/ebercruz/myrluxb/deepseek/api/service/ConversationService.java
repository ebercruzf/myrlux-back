package com.ebercruz.myrluxb.deepseek.api.service;

import com.ebercruz.myrluxb.deepseek.api.model.ChatMessage;

import java.util.UUID;


import java.util.List;


public interface ConversationService {
    /**
     * Construye un prompt contextual basado en el historial de la conversación
     */
    String buildContextualPrompt(UUID conversationId, String systemPrompt, String userPrompt);

    /**
     * Agrega un mensaje del asistente a la conversación
     */
    void addAssistantMessage(UUID conversationId, String content);

    /**
     * Agrega un mensaje del usuario a la conversación
     */
    void addUserMessage(UUID conversationId, String content);

    /**
     * Limpia el historial de una conversación
     */
    void clearConversation(UUID conversationId);

    /**
     * Obtiene el historial de mensajes de una conversación
     */
    List<ChatMessage> getConversationHistory(UUID conversationId);
}
