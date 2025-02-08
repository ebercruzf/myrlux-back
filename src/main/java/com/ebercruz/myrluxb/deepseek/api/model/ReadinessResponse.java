package com.ebercruz.myrluxb.deepseek.api.model;

import java.net.URI;
import java.util.Objects;
import com.ebercruz.myrluxb.deepseek.api.model.ReadinessResponseDependencies;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ReadinessResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-27T19:54:12.066667-06:00[America/Mexico_City]")
public class ReadinessResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Gets or Sets status
   */
  public enum StatusEnum {
    UP("UP"),
    
    DOWN("DOWN");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private StatusEnum status;

  private ReadinessResponseDependencies dependencies;

  public ReadinessResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ReadinessResponse(StatusEnum status, ReadinessResponseDependencies dependencies) {
    this.status = status;
    this.dependencies = dependencies;
  }

  public ReadinessResponse status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @NotNull 
  @Schema(name = "status", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public ReadinessResponse dependencies(ReadinessResponseDependencies dependencies) {
    this.dependencies = dependencies;
    return this;
  }

  /**
   * Get dependencies
   * @return dependencies
  */
  @NotNull @Valid 
  @Schema(name = "dependencies", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("dependencies")
  public ReadinessResponseDependencies getDependencies() {
    return dependencies;
  }

  public void setDependencies(ReadinessResponseDependencies dependencies) {
    this.dependencies = dependencies;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReadinessResponse readinessResponse = (ReadinessResponse) o;
    return Objects.equals(this.status, readinessResponse.status) &&
        Objects.equals(this.dependencies, readinessResponse.dependencies);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, dependencies);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReadinessResponse {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    dependencies: ").append(toIndentedString(dependencies)).append("\n");
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

