package com.mortgage.valuation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO matching the requested simplified form JSON model used by downstream systems.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ValuationResponse {

    @JsonProperty("createdUtc")
    private String createdUtc;

    @JsonProperty("lastUpdatedUtc")
    private String lastUpdatedUtc;

    @JsonProperty("formCode")
    private String formCode;

    @JsonProperty("status")
    private String status;

    @JsonProperty("transactionId")
    private String transactionId;

    @JsonProperty("answers")
    private List<Answer> answers;

    @JsonProperty("answersFormVersions")
    private List<String> answersFormVersions;

    public static class Answer {

        @JsonProperty("questionId")
        private String questionId;

        @JsonProperty("answerType")
        private String answerType;

        @JsonProperty("value")
        private Object value;

        @JsonProperty("questionLinkOverridden")
        private boolean questionLinkOverridden;
    }
}
