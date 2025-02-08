package com.ebercruz.myrluxb.deepseek.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class LLMRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotNull
  private String prompt;
  private Double temperature = 0.7d;
  private Boolean streamPreferred;
  private Integer expectedResponseLength;
  private UUID conversationId;
  private String systemPrompt;
  private Boolean useContext = true;

  // Constructores
  public LLMRequest() {
    super();
  }

  public LLMRequest(String prompt) {
    this.prompt = prompt;
  }

  // Getters y Setters
  @Schema(description = "El texto de entrada para el modelo", required = true)
  @JsonProperty("prompt")
  public String getPrompt() {
    return prompt;
  }

  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  @DecimalMin("0") @DecimalMax("1")
  @Schema(description = "Controla la aleatoriedad de las respuestas")
  public Double getTemperature() {
    return temperature;
  }

  public void setTemperature(Double temperature) {
    this.temperature = temperature;
  }

  @Schema(description = "Indica si se prefiere respuesta en streaming")
  public Boolean getStreamPreferred() {
    return streamPreferred;
  }

  public void setStreamPreferred(Boolean streamPreferred) {
    this.streamPreferred = streamPreferred;
  }

  @Schema(description = "Longitud esperada de la respuesta en caracteres")
  public Integer getExpectedResponseLength() {
    return expectedResponseLength;
  }

  public void setExpectedResponseLength(Integer expectedResponseLength) {
    this.expectedResponseLength = expectedResponseLength;
  }

  @Schema(description = "ID único de la conversación")
  public UUID getConversationId() {
    return conversationId;
  }

  public void setConversationId(UUID conversationId) {
    this.conversationId = conversationId;
  }

  @Schema(description = "Instrucciones del sistema para el modelo")
  public String getSystemPrompt() {
    return systemPrompt;
  }

  public void setSystemPrompt(String systemPrompt) {
    this.systemPrompt = systemPrompt;
  }

  @Schema(description = "Indica si se debe usar el contexto de la conversación")
  public Boolean getUseContext() {
    return useContext;
  }

  public void setUseContext(Boolean useContext) {
    this.useContext = useContext;
  }

  // Métodos de utilidad
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LLMRequest that = (LLMRequest) o;
    return Objects.equals(prompt, that.prompt) &&
            Objects.equals(temperature, that.temperature) &&
            Objects.equals(streamPreferred, that.streamPreferred) &&
            Objects.equals(expectedResponseLength, that.expectedResponseLength) &&
            Objects.equals(conversationId, that.conversationId) &&
            Objects.equals(systemPrompt, that.systemPrompt) &&
            Objects.equals(useContext, that.useContext);
  }

  @Override
  public int hashCode() {
    return Objects.hash(prompt, temperature, streamPreferred, expectedResponseLength,
            conversationId, systemPrompt, useContext);
  }

  @Override
  public String toString() {
    return "LLMRequest{" +
            "prompt='" + prompt + '\'' +
            ", temperature=" + temperature +
            ", streamPreferred=" + streamPreferred +
            ", expectedResponseLength=" + expectedResponseLength +
            ", conversationId=" + conversationId +
            ", systemPrompt='" + systemPrompt + '\'' +
            ", useContext=" + useContext +
            '}';
  }
}