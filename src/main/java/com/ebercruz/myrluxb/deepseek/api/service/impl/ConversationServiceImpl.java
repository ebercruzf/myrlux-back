package com.ebercruz.myrluxb.deepseek.api.service.impl;



import com.ebercruz.myrluxb.deepseek.api.model.ChatMessage;
import com.ebercruz.myrluxb.deepseek.api.service.ConversationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Implementación del servicio de gestión de conversaciones para el chat con LLM.
 * Esta clase maneja el estado y contexto de las conversaciones entre usuarios y el modelo DeepSeek-R1.
 *
 * Características principales:
 * - Almacenamiento thread-safe de conversaciones usando ConcurrentHashMap
 * - Manejo de estados de conversación con UUIDs únicos
 * - Construcción de prompts contextuales para mejorar la coherencia de respuestas
 * - Logging detallado para debugging y monitoreo
 */
@Service
public class ConversationServiceImpl implements ConversationService {
    private static final Logger logger = LoggerFactory.getLogger(ConversationServiceImpl.class);

    /**
     * Almacenamiento principal de conversaciones.
     * - Key: UUID de la conversación
     * - Value: Lista ordenada cronológicamente de mensajes
     * Thread-safe para múltiples usuarios concurrentes
     */
    private final Map<UUID, List<ChatMessage>> conversations = new ConcurrentHashMap<>();

    /**
     * Agrega un mensaje del usuario al historial de la conversación
     * @param conversationId ID único de la conversación
     * @param content Contenido del mensaje del usuario
     */
    @Override
    public void addUserMessage(UUID conversationId, String content) {
        addMessage(conversationId, "user", content);
    }

    /**
     * Agrega una respuesta del asistente (LLM) al historial
     * @param conversationId ID único de la conversación
     * @param content Contenido de la respuesta del modelo
     */
    @Override
    public void addAssistantMessage(UUID conversationId, String content) {
        addMessage(conversationId, "assistant", content);
    }

    /**
     * Método interno para agregar mensajes al historial.
     * Maneja la creación y almacenamiento thread-safe de mensajes.
     *
     * @param conversationId ID de la conversación
     * @param role Rol del mensaje ("user" o "assistant")
     * @param content Contenido del mensaje
     */
    private void addMessage(UUID conversationId, String role, String content) {
        ChatMessage message = new ChatMessage();
        message.setId(UUID.randomUUID());
        message.setRole(role);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setConversationId(conversationId);

        // Uso de computeIfAbsent para manejo thread-safe de nuevas conversaciones
        conversations.computeIfAbsent(conversationId, k -> new ArrayList<>()).add(message);
        logger.debug("Mensaje agregado a la conversación {}: {} - {}", conversationId, role, content);
    }

    /**
     * Construye un prompt contextual para el modelo LLM.
     * Integra:
     * 1. Instrucciones del sistema (si existen)
     * 2. Historial de la conversación
     * 3. Nuevo prompt del usuario
     *
     * @param conversationId ID de la conversación
     * @param systemPrompt Instrucciones del sistema (opcional)
     * @param userPrompt Nuevo prompt del usuario
     * @return Prompt completo con contexto
     */
    @Override
    public String buildContextualPrompt(UUID conversationId, String systemPrompt, String userPrompt) {
        StringBuilder contextBuilder = new StringBuilder();

        // Integración de system prompt para control de comportamiento
        if (systemPrompt != null && !systemPrompt.isEmpty()) {
            contextBuilder.append("System: ").append(systemPrompt).append("\n\n");
        }

        // Recuperación thread-safe del historial
        List<ChatMessage> messages = conversations.getOrDefault(conversationId, new ArrayList<>());
        for (ChatMessage message : messages) {
            contextBuilder.append(message.getRole())
                    .append(": ")
                    .append(message.getContent())
                    .append("\n");
        }

        // Formato final del prompt
        contextBuilder.append("Human: ").append(userPrompt).append("\nAssistant: ");

        String fullPrompt = contextBuilder.toString();
        logger.debug("Prompt contextual generado: {}", fullPrompt);

        return fullPrompt;
    }

    /**
     * Elimina una conversación y su historial
     * Útil para limpiar memoria y respetar privacidad
     */
    @Override
    public void clearConversation(UUID conversationId) {
        conversations.remove(conversationId);
        logger.info("Conversación eliminada: {}", conversationId);
    }

    /**
     * Recupera el historial completo de una conversación
     * Retorna una copia defensiva para prevenir modificaciones externas
     */
    @Override
    public List<ChatMessage> getConversationHistory(UUID conversationId) {
        return new ArrayList<>(conversations.getOrDefault(conversationId, new ArrayList<>()));
    }
}