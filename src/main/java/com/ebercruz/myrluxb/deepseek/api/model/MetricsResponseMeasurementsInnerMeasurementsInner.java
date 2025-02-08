package com.ebercruz.myrluxb.deepseek.api.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * MetricsResponseMeasurementsInnerMeasurementsInner
 */

@JsonTypeName("MetricsResponse_measurements_inner_measurements_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-27T19:54:12.066667-06:00[America/Mexico_City]")
public class MetricsResponseMeasurementsInnerMeasurementsInner implements Serializable {

  private static final long serialVersionUID = 1L;

  private String statistic;

  private BigDecimal value;

  public MetricsResponseMeasurementsInnerMeasurementsInner statistic(String statistic) {
    this.statistic = statistic;
    return this;
  }

  /**
   * Get statistic
   * @return statistic
  */
  
  @Schema(name = "statistic", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("statistic")
  public String getStatistic() {
    return statistic;
  }

  public void setStatistic(String statistic) {
    this.statistic = statistic;
  }

  public MetricsResponseMeasurementsInnerMeasurementsInner value(BigDecimal value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
  */
  @Valid 
  @Schema(name = "value", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("value")
  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MetricsResponseMeasurementsInnerMeasurementsInner metricsResponseMeasurementsInnerMeasurementsInner = (MetricsResponseMeasurementsInnerMeasurementsInner) o;
    return Objects.equals(this.statistic, metricsResponseMeasurementsInnerMeasurementsInner.statistic) &&
        Objects.equals(this.value, metricsResponseMeasurementsInnerMeasurementsInner.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statistic, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MetricsResponseMeasurementsInnerMeasurementsInner {\n");
    sb.append("    statistic: ").append(toIndentedString(statistic)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

