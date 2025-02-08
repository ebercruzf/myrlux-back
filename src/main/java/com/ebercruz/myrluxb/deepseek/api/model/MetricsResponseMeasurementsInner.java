package com.ebercruz.myrluxb.deepseek.api.model;

import java.net.URI;
import java.util.Objects;
import com.ebercruz.myrluxb.deepseek.api.model.MetricsResponseMeasurementsInnerMeasurementsInner;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * MetricsResponseMeasurementsInner
 */

@JsonTypeName("MetricsResponse_measurements_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-27T19:54:12.066667-06:00[America/Mexico_City]")
public class MetricsResponseMeasurementsInner implements Serializable {

  private static final long serialVersionUID = 1L;

  private String name;

  @Valid
  private List<@Valid MetricsResponseMeasurementsInnerMeasurementsInner> measurements;

  public MetricsResponseMeasurementsInner name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */
  
  @Schema(name = "name", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MetricsResponseMeasurementsInner measurements(List<@Valid MetricsResponseMeasurementsInnerMeasurementsInner> measurements) {
    this.measurements = measurements;
    return this;
  }

  public MetricsResponseMeasurementsInner addMeasurementsItem(MetricsResponseMeasurementsInnerMeasurementsInner measurementsItem) {
    if (this.measurements == null) {
      this.measurements = new ArrayList<>();
    }
    this.measurements.add(measurementsItem);
    return this;
  }

  /**
   * Get measurements
   * @return measurements
  */
  @Valid 
  @Schema(name = "measurements", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("measurements")
  public List<@Valid MetricsResponseMeasurementsInnerMeasurementsInner> getMeasurements() {
    return measurements;
  }

  public void setMeasurements(List<@Valid MetricsResponseMeasurementsInnerMeasurementsInner> measurements) {
    this.measurements = measurements;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MetricsResponseMeasurementsInner metricsResponseMeasurementsInner = (MetricsResponseMeasurementsInner) o;
    return Objects.equals(this.name, metricsResponseMeasurementsInner.name) &&
        Objects.equals(this.measurements, metricsResponseMeasurementsInner.measurements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, measurements);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MetricsResponseMeasurementsInner {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    measurements: ").append(toIndentedString(measurements)).append("\n");
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

