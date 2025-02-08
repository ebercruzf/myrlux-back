package com.ebercruz.myrluxb.deepseek.api.model;

import java.net.URI;
import java.util.Objects;
import com.ebercruz.myrluxb.deepseek.api.model.MetricsResponseMeasurementsInner;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * MetricsResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-27T19:54:12.066667-06:00[America/Mexico_City]")
public class MetricsResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  @Valid
  private List<@Valid MetricsResponseMeasurementsInner> measurements = new ArrayList<>();

  public MetricsResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MetricsResponse(List<@Valid MetricsResponseMeasurementsInner> measurements) {
    this.measurements = measurements;
  }

  public MetricsResponse measurements(List<@Valid MetricsResponseMeasurementsInner> measurements) {
    this.measurements = measurements;
    return this;
  }

  public MetricsResponse addMeasurementsItem(MetricsResponseMeasurementsInner measurementsItem) {
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
  @NotNull @Valid 
  @Schema(name = "measurements", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("measurements")
  public List<@Valid MetricsResponseMeasurementsInner> getMeasurements() {
    return measurements;
  }

  public void setMeasurements(List<@Valid MetricsResponseMeasurementsInner> measurements) {
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
    MetricsResponse metricsResponse = (MetricsResponse) o;
    return Objects.equals(this.measurements, metricsResponse.measurements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(measurements);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MetricsResponse {\n");
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

