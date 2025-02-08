package com.ebercruz.myrluxb.deepseek.api.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ChatMessage {

    private UUID id;
    private String role;
    private String content;
    private LocalDateTime timestamp;
    private UUID conversationId;

    public ChatMessage() {

    }
    public ChatMessage(UUID id, String role, String content, LocalDateTime timestamp, UUID conversationId) {
        this.id = id;
        this.role = role;
        this.content = content;
        this.timestamp = timestamp;
        this.conversationId = conversationId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getConversationId() {
        return conversationId;
    }

    public void setConversationId(UUID conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                ", conversationId=" + conversationId +
                '}';
    }


}
