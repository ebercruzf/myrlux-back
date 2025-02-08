package com.ebercruz.myrluxb.deepseek.api.model;

import java.net.URI;
import java.util.Objects;
import com.ebercruz.myrluxb.deepseek.api.model.HealthResponse;
import com.ebercruz.myrluxb.deepseek.api.model.HealthResponseComponents;
import com.ebercruz.myrluxb.deepseek.api.model.LLMResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import org.openapitools.jackson.nullable.JsonNullable;
import java.util.NoSuchElementException;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;


@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-01-27T19:54:12.066667-06:00[America/Mexico_City]")
public interface ApiResponseData extends Serializable {
}
