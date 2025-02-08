package com.ebercruz.myrluxb.deepseek.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import org.openapitools.jackson.nullable.JsonNullable;
import java.util.NoSuchElementException;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * LLMResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-27T19:54:12.066667-06:00[America/Mexico_City]")
public class LLMResponse implements Serializable, ApiResponseData {

  private static final long serialVersionUID = 1L;

  private String response;

  private String model;

  private Integer totalTokens;

  public LLMResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public LLMResponse(String response, String model) {
    this.response = response;
    this.model = model;
  }

  public LLMResponse response(String response) {
    this.response = response;
    return this;
  }

  /**
   * Respuesta generada por el modelo
   * @return response
  */
  @NotNull 
  @Schema(name = "response", description = "Respuesta generada por el modelo", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("response")
  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public LLMResponse model(String model) {
    this.model = model;
    return this;
  }

  /**
   * Nombre del modelo utilizado
   * @return model
  */
  @NotNull 
  @Schema(name = "model", description = "Nombre del modelo utilizado", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("model")
  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public LLMResponse totalTokens(Integer totalTokens) {
    this.totalTokens = totalTokens;
    return this;
  }

  /**
   * Número total de tokens utilizados
   * @return totalTokens
  */
  
  @Schema(name = "totalTokens", description = "Número total de tokens utilizados", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("totalTokens")
  public Integer getTotalTokens() {
    return totalTokens;
  }

  public void setTotalTokens(Integer totalTokens) {
    this.totalTokens = totalTokens;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LLMResponse llMResponse = (LLMResponse) o;
    return Objects.equals(this.response, llMResponse.response) &&
        Objects.equals(this.model, llMResponse.model) &&
        Objects.equals(this.totalTokens, llMResponse.totalTokens);
  }

  @Override
  public int hashCode() {
    return Objects.hash(response, model, totalTokens);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LLMResponse {\n");
    sb.append("    response: ").append(toIndentedString(response)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    totalTokens: ").append(toIndentedString(totalTokens)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

