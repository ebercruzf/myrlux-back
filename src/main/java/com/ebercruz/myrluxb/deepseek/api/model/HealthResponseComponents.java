package com.ebercruz.myrluxb.deepseek.api.model;

import java.net.URI;
import java.util.Objects;
import com.ebercruz.myrluxb.deepseek.api.model.ComponentHealth;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * HealthResponseComponents
 */

@JsonTypeName("HealthResponse_components")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-27T19:54:12.066667-06:00[America/Mexico_City]")
public class HealthResponseComponents implements Serializable {

  private static final long serialVersionUID = 1L;

  private ComponentHealth ollama;

  private ComponentHealth diskSpace;

  private ComponentHealth db;

  public HealthResponseComponents ollama(ComponentHealth ollama) {
    this.ollama = ollama;
    return this;
  }

  /**
   * Get ollama
   * @return ollama
  */
  @Valid 
  @Schema(name = "ollama", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ollama")
  public ComponentHealth getOllama() {
    return ollama;
  }

  public void setOllama(ComponentHealth ollama) {
    this.ollama = ollama;
  }

  public HealthResponseComponents diskSpace(ComponentHealth diskSpace) {
    this.diskSpace = diskSpace;
    return this;
  }

  /**
   * Get diskSpace
   * @return diskSpace
  */
  @Valid 
  @Schema(name = "diskSpace", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("diskSpace")
  public ComponentHealth getDiskSpace() {
    return diskSpace;
  }

  public void setDiskSpace(ComponentHealth diskSpace) {
    this.diskSpace = diskSpace;
  }

  public HealthResponseComponents db(ComponentHealth db) {
    this.db = db;
    return this;
  }

  /**
   * Get db
   * @return db
  */
  @Valid 
  @Schema(name = "db", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("db")
  public ComponentHealth getDb() {
    return db;
  }

  public void setDb(ComponentHealth db) {
    this.db = db;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HealthResponseComponents healthResponseComponents = (HealthResponseComponents) o;
    return Objects.equals(this.ollama, healthResponseComponents.ollama) &&
        Objects.equals(this.diskSpace, healthResponseComponents.diskSpace) &&
        Objects.equals(this.db, healthResponseComponents.db);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ollama, diskSpace, db);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class HealthResponseComponents {\n");
    sb.append("    ollama: ").append(toIndentedString(ollama)).append("\n");
    sb.append("    diskSpace: ").append(toIndentedString(diskSpace)).append("\n");
    sb.append("    db: ").append(toIndentedString(db)).append("\n");
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

