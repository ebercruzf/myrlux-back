package com.ebercruz.myrluxb.deepseek.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
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
 * ReadinessResponseDependencies
 */

@JsonTypeName("ReadinessResponse_dependencies")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-27T19:54:12.066667-06:00[America/Mexico_City]")
public class ReadinessResponseDependencies implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Gets or Sets ollama
   */
  public enum OllamaEnum {
    UP("UP"),
    
    DOWN("DOWN");

    private String value;

    OllamaEnum(String value) {
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
    public static OllamaEnum fromValue(String value) {
      for (OllamaEnum b : OllamaEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private OllamaEnum ollama;

  /**
   * Gets or Sets database
   */
  public enum DatabaseEnum {
    UP("UP"),
    
    DOWN("DOWN");

    private String value;

    DatabaseEnum(String value) {
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
    public static DatabaseEnum fromValue(String value) {
      for (DatabaseEnum b : DatabaseEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private DatabaseEnum database;

  public ReadinessResponseDependencies ollama(OllamaEnum ollama) {
    this.ollama = ollama;
    return this;
  }

  /**
   * Get ollama
   * @return ollama
  */
  
  @Schema(name = "ollama", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ollama")
  public OllamaEnum getOllama() {
    return ollama;
  }

  public void setOllama(OllamaEnum ollama) {
    this.ollama = ollama;
  }

  public ReadinessResponseDependencies database(DatabaseEnum database) {
    this.database = database;
    return this;
  }

  /**
   * Get database
   * @return database
  */
  
  @Schema(name = "database", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("database")
  public DatabaseEnum getDatabase() {
    return database;
  }

  public void setDatabase(DatabaseEnum database) {
    this.database = database;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReadinessResponseDependencies readinessResponseDependencies = (ReadinessResponseDependencies) o;
    return Objects.equals(this.ollama, readinessResponseDependencies.ollama) &&
        Objects.equals(this.database, readinessResponseDependencies.database);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ollama, database);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReadinessResponseDependencies {\n");
    sb.append("    ollama: ").append(toIndentedString(ollama)).append("\n");
    sb.append("    database: ").append(toIndentedString(database)).append("\n");
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

