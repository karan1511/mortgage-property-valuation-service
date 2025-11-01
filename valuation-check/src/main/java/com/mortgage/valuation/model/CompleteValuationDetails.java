package com.mortgage.valuation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(value = PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class CompleteValuationDetails extends BaseVRMetadataProperties {
    private String loanId;
    private String transactionRequestId;
    private String valuationStatus;
    private String surveyorBranchId;
    private String milestoneId;
    private String reason;
    private String appointmentDate;
    private String instructionRequestId;
    private String transactionRequestMilestoneId;
    private String transactionRequestType;
    private String additionalInformation;
    private String propertyName;
    private String dateAchieved;
    private String datePredicted;
    private String timeWindowStart;
    private String timeWindowEnd;
}