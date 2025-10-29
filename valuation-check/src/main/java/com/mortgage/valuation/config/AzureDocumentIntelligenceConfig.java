package com.mortgage.valuation.config;

import com.azure.ai.documentintelligence.DocumentIntelligenceClient;
import com.azure.ai.documentintelligence.DocumentIntelligenceClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Azure Document Intelligence configuration for PDF text extraction.
 */
@Configuration
@ConfigurationProperties(prefix = "azure.document-intelligence")
public class AzureDocumentIntelligenceConfig {

    private String endpoint;
    private String apiKey;

    @Bean
    public DocumentIntelligenceClient documentIntelligenceClient() {
        return new DocumentIntelligenceClientBuilder()
                .endpoint(endpoint)
                .credential(new AzureKeyCredential(apiKey))
                .buildClient();
    }

    // Getters and setters
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
