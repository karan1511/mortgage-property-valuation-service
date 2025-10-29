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
                    // Provide the model a strict JSON schema matching our ValuationResponse DTO and
                    // instructions to return only valid JSON compatible with Jackson deserialization.
                    return String.format("""
                            Please analyze the following property valuation report and EXTRACT structured information.

                            Requirements (strict):
                            - Return a single JSON object and NOTHING ELSE (no explanation, no surrounding markdown, no trailing text).
                            - Follow exactly the JSON structure shown below. Use null for unknown/missing values.
                            - Use numbers for numeric fields (no quotes). Dates/timestamps must be ISO-8601.
                            - Monetary values must be plain numbers in GBP (e.g. 350000).
                            - The "requestId" field MUST be exactly: "%s".
                            - Ensure the JSON parses with a strict JSON parser (no comments, no extra commas).

                            Schema to produce (MATCHES ValuationResponse DTO):
                            {
                                "requestId": "%s",
                                "propertyAddress": {
                                    "line1": "...",
                                    "line2": "...",
                                    "city": "...",
                                    "postcode": "...",
                                    "county": "..."
                                },
                                "valuationDetails": {
                                    "estimatedValue": 350000,
                                    "valuationRange": { "minValue": 340000, "maxValue": 360000 },
                                    "valuationMethod": "Comparative",
                                    "valuationDate": "2025-10-24"
                                },
                                "propertyCharacteristics": {
                                    "propertyType": "Detached",
                                    "bedrooms": 3,
                                    "bathrooms": 2,
                                    "receptionRooms": 1,
                                    "totalFloorArea": 85.0,
                                    "plotSize": null,
                                    "yearBuilt": 1990,
                                    "condition": "Good"
                                },
                                "marketAnalysis": {
                                    "localMarketTrend": "Stable",
                                    "averagePricePerSqFt": null,
                                    "comparableProperties": [
                                        { "address": "...", "salePrice": 345000, "saleDate": "2025-01-15", "distance": 0.5 }
                                    ]
                                },
                                "riskAssessment": {
                                    "overallRisk": "Low",
                                    "riskFactors": ["Flood risk"],
                                    "riskScore": 10
                                },
                                "recommendations": ["Recommendation 1"],
                                "confidenceScore": 0.87
                            }

                            Valuation Report Text:
                            %s

                            IMPORTANT: Respond with VALID JSON only and match the schema exactly. If a value is unknown, set it to null.
                            """, requestId, requestId, extractedText);
            }

    /**
     * Gets the system prompt for the AI model.
     */
    private String getSystemPrompt() {
        return """
            You are an expert UK property valuation analyst. Your task is to extract structured information from property valuation reports and present it in a consistent JSON format.
            
            Key requirements:
            - Extract accurate property details including address, characteristics, and valuation information
            - Identify market trends and comparable properties
            - Assess risk factors and provide recommendations
            - Use UK property terminology and standards
            - Ensure all monetary values are in GBP
            - Provide a confidence score between 0.0 and 1.0 based on the clarity and completeness of the information
            - Return only valid JSON, no additional text or formatting
            
            If any information is not available or unclear, use null or appropriate default values.
            """;
    }

    /**
     * Parses the AI response into a ValuationResponse object.
     */
    private ValuationResponse parseValuationResponse(String responseContent, String requestId) throws AzureOpenAIServiceException {
        try {
            // Clean the response content to extract JSON
            String jsonContent = extractJsonFromResponse(responseContent);
            
            ValuationResponse response = objectMapper.readValue(jsonContent, ValuationResponse.class);
            response.setRequestId(requestId);
            
            return response;
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
