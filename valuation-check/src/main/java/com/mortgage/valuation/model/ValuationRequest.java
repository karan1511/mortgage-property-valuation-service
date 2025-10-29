package com.mortgage.valuation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for valuation processing.
 */
public class ValuationRequest {

    @NotBlank(message = "Request ID is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Request ID must contain only alphanumeric characters")
    @JsonProperty("requestId")
    private String requestId;

    @NotBlank(message = "Loan Application ID is required")
    @Size(min = 1, max = 18)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Loan Application ID must contain only alphanumeric characters")
    @JsonProperty("loanApplicationId")
    private String loanApplicationId;

    public ValuationRequest() {}

    public ValuationRequest(String requestId, String loanApplicationId) {
        this.requestId = requestId;
        this.loanApplicationId = loanApplicationId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(String loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }

    @Override
    public String toString() {
        return "ValuationRequest{" +
                "requestId='" + requestId + '\'' +
                ", loanApplicationId='" + loanApplicationId + '\'' +
                '}';
    }
}
