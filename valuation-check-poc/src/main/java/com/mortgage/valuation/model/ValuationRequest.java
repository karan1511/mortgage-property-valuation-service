package com.mortgage.valuation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Request DTO for valuation processing.
 */
public class ValuationRequest {

    @NotBlank(message = "Request ID is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Request ID must contain only alphanumeric characters")
    @JsonProperty("requestId")
    private String requestId;

    public ValuationRequest() {}

    public ValuationRequest(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "ValuationRequest{" +
                "requestId='" + requestId + '\'' +
                '}';
    }
}
