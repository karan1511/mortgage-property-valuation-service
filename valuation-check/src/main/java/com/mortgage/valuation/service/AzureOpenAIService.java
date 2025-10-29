package com.mortgage.valuation.service;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mortgage.valuation.config.AzureOpenAIConfig;
import com.mortgage.valuation.model.ValuationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for processing extracted text with Azure OpenAI to generate structured valuation data.
 */
@Service
public class AzureOpenAIService {

    private static final Logger logger = LoggerFactory.getLogger(AzureOpenAIService.class);

    private final OpenAIClient openAIClient;
    private final AzureOpenAIConfig config;
    private final ObjectMapper objectMapper;

    @Autowired
    public AzureOpenAIService(OpenAIClient openAIClient, AzureOpenAIConfig config) {
        this.openAIClient = openAIClient;
        this.config = config;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Processes extracted text to generate structured valuation data using Azure OpenAI.
     *
     * @param extractedText The text extracted from the PDF
     * @param requestId The request ID for context
     * @return Structured valuation response
     * @throws AzureOpenAIServiceException if processing fails
     */
    public ValuationResponse processValuationText(String extractedText, String requestId) throws AzureOpenAIServiceException {
        logger.info("Processing valuation text with Azure OpenAI for request: {}", requestId);

        try {
            String prompt = buildValuationPrompt(extractedText, requestId);
            
            List<ChatRequestMessage> messages = new ArrayList<>();
            messages.add(new ChatRequestSystemMessage(getSystemPrompt()));
            messages.add(new ChatRequestUserMessage(prompt));

            ChatCompletionsOptions options = new ChatCompletionsOptions(messages)
                    .setMaxTokens(config.getMaxTokens())
                    .setTemperature(config.getTemperature())
                    .setTopP(0.95);

            ChatCompletions chatCompletions = openAIClient.getChatCompletions(config.getDeploymentName(), options);
            
            String responseContent = chatCompletions.getChoices().get(0).getMessage().getContent();
            logger.info("Received response from Azure OpenAI: {} characters", responseContent.length());

            // Parse the JSON response
            ValuationResponse valuationResponse = parseValuationResponse(responseContent, requestId);
            
            logger.info("Successfully processed valuation for request: {}", requestId);
            return valuationResponse;

        } catch (Exception e) {
            logger.error("Error processing valuation text with Azure OpenAI: {}", e.getMessage(), e);
            throw new AzureOpenAIServiceException("Failed to process valuation with Azure OpenAI: " + e.getMessage(), e);
        }
    }

    /**
     * Builds the valuation prompt for the extracted text.
     */
    private String buildValuationPrompt(String extractedText, String requestId) {
        // Build a strict instruction that converts unstructured report text into
        // the required form JSON structure used by downstream systems.
        // The model MUST return only valid JSON, nothing else.
        return String.format("""
                You are given extracted text from a single property valuation report. Use the system instructions for mapping and output rules.
                
                Context:
                - requestId: %s
                
                --- BEGIN EXTRACTED TEXT ---
                %s
                --- END EXTRACTED TEXT ---
                
                TASK:
                1) Produce the complete JSON envelope exactly as specified in the system prompt.
                2) Populate createdUtc and lastUpdatedUtc with the CURRENT UTC timestamp (ISO-8601 with Z).
                3) Populate "answers" with entries for every questionId you can confidently extract based on the hardcoded mapping rules in the system prompt.
                5) If value for any questionId is not found or is an empty array, then in "answers", set the "value": null.
                6) Return ONLY the JSON object; do not include any extra text.
                
                Remember: do not fabricate values. When in doubt, set value to null.
                """, requestId, extractedText);
    }

    /**
     * Gets the system prompt for the AI model.
     */
    private String getSystemPrompt() {
        return """
        You are an expert UK property valuation data extraction assistant.
        Your ONLY job is to convert unstructured valuation report text into one precise JSON object and nothing else.

        REQUIREMENTS:
        1) OUTPUT ONLY: Return exactly one valid JSON object (no explanations, markdown, or metadata).
        2) JSON SCHEMA:
           {
             "createdUtc": "<ISO-8601 UTC timestamp with Z>",
             "lastUpdatedUtc": "<ISO-8601 UTC timestamp with Z>",
             "formCode": "VR4B",
             "status": "inProgress",
             "transactionId": "Translated from legacy report",
             "answers": [
               {
                 "questionId": "<string>",
                 "answerType": "<text|number|decimal|date|options|photos|boolean>",
                 "value": <string | number | array | null>,
                 "questionLinkOverridden": false
               }
             ],
             "answersFormVersions": []
           }

        TOP-LEVEL RULES:
        - "formCode" must always be "VR4B".
        - "status" must be "inProgress".
        - "transactionId" must always be "Translated from legacy report".
        - "createdUtc" and "lastUpdatedUtc" use CURRENT UTC timestamp (ISO-8601 with Z).
        - "answersFormVersions" is always an empty array.
        - All questionLinkOverridden = false.
        - No missing keys or extra keys inside "answers".

        VALUE RULES:
        - Use plain numbers (no commas, £, or quotes).
        - For decimals: include ".0" if integer value.
        - Dates: use format DD/MM/YYYY.
        - Options: always arrays of strings (e.g., ["Yes"], ["Purchase"]).
        - If data missing, set value to null.
        - RecommendedRetentionAmount: if not found, set 0.0 (default).
        
        MAPPING TABLE (hardcoded):
        - VR4B_LenderReference                               -> text
        - VR4B_ApplicantNames                                -> text
        - VR4B_Transaction                                   -> options
        - VR4B_PurchasePriceEstimatedValuePpEv               -> decimal
        - VR4B_PropertyAddressHouseFlat                      -> text (extract house/flat number only)
        - VR4B_RoadNumber                                    -> text (extract first road token after house number)
        - VR4B_RoadName                                      -> text (remaining road/street name)
        - VR4B_Area                                          -> text
        - VR4B_Town                                          -> text
        - VR4B_County                                        -> text
        - VR4B_PostCode                                      -> text
        - VR4B_AddressMatch                                  -> options
        - VR4B_ActualAddressIfAddressNotAMatch               -> text
        - VR4B_InspectionDate                                -> date
        - VR4B_ReportDate                                    -> date
        - VR4B_CertificationIsThePropertySuitableSecurity    -> options
        - VR4B_IfPropertyIsNotSuitableSecurityPleaseIdentifyWhy -> options
        - VR4B_SecurityOther                                 -> text
        - VR4B_AreAllFlatsMarketableAndMortgageable          -> options
        - VR4B_IsThePropertyBuiltOfTraditionalConstruction   -> options
        - VR4B_SpecialistReportsRequired                     -> options
        - VR4B_AnyApplicableLicensingSchemeInPlace           -> options
        - VR4B_MarketValue                                   -> decimal (maps from previous VR4B_ValuationMarketValueAggregate)
        - VR4B_MarketValAfterWorksIssuesResolved             -> decimal
        - VR4B_MarketRent                                    -> decimal (maps from VR4B_MarketRentAggregate)
        - VR4B_ReinstatementCost                             -> decimal
        - VR4B_DoesTheReinstatementCostCarryHighUncertaintyDueToSpecialistBuildingFeaturesListingOrOtherMatters -> options
        - VR4B_RecommendedRetentionAmount                    -> decimal (default 0.0 if missing)
        - VR4B_Tenure                                        -> options (maps from VR4B_TitleTenureAndAccessTenure)
        - VR4B_AnyFlyingCreepingFhEvident                    -> options
        - VR4B_IfLeaseholdWhatIsTheUnexpiredLeaseTerm        -> number
        - VR4B_WhatPercentageOfTotalAreaIsFfh                -> number
        - VR4B_DoesThereAppearToBeSharedAccess               -> options
        - VR4B_IsAccessFromPublicLand                        -> options
        - VR4B_IsAccessMadeUp                                -> options
        - VR4B_SecurityProperyTypeAndStyle                   -> options
        - VR4B_NumberOfFloorsInTheBuilding                   -> number
        - VR4B_HowManyFlatsAreInTheEntireBuilding            -> number
        - VR4B_IsThePropertyServedByALift                    -> options
        - VR4B_IsThePropertyANewBuild                        -> options

        - Include VR4B_ValuersDeclarationValuerName, VR4B_Qualification, VR4B_RicsNumber, VR4B_FirmName,
          VR4B_FirmAddressLine1-3, VR4B_TelephoneNo, VR4B_EmailAddressLine1, VR4B_LenderReportPhotographs
          with null values if not found.

        OUTPUT RULE:
        - Return ONLY the JSON object (no markdown, no text explanation).
        - Do not include extra commas, logs, or summaries.
        """;
    }

    /**
     * Parses the AI response into a ValuationResponse object.
     */
    private ValuationResponse parseValuationResponse(String responseContent, String requestId) throws AzureOpenAIServiceException {
        try {
            // Clean the response content to extract JSON
            String jsonContent = extractJsonFromResponse(responseContent);
            
            return objectMapper.readValue(jsonContent, ValuationResponse.class);
        } catch (JsonProcessingException e) {
            logger.error("Failed to parse AI response as JSON: {}", e.getMessage());
            throw new AzureOpenAIServiceException("Failed to parse AI response: " + e.getMessage(), e);
        }
    }

    /**
     * Extracts JSON content from the AI response, handling cases where the response might contain additional text.
     */
    private String extractJsonFromResponse(String responseContent) {
        // Look for JSON content between curly braces
        int startIndex = responseContent.indexOf('{');
        int endIndex = responseContent.lastIndexOf('}');
        
        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return responseContent.substring(startIndex, endIndex + 1);
        }
        
        // If no JSON found, return the original content
        return responseContent.trim();
    }

    /**
     * Custom exception for Azure OpenAI service errors.
     */
    public static class AzureOpenAIServiceException extends Exception {
        public AzureOpenAIServiceException(String message) {
            super(message);
        }

        public AzureOpenAIServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
