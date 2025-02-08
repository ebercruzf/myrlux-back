package com.ebercruz.myrluxb.deepseek.api.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Conversation {

    private UUID id;
    private List<ChatMessage> messages;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Conversation(UUID id, List<ChatMessage> messages, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.messages = messages;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
        this.updatedAt = LocalDateTime.now();
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", messages=" + messages +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
