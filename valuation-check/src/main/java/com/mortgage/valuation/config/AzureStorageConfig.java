package com.mortgage.valuation.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for Azure Storage.
 */
@Configuration
@ConfigurationProperties(prefix = "azure.storage")
public class AzureStorageConfig {

    private String connectionString;
    private String containerName = "lending-property";
    private String valuationReportsPath = "property-valuation-reports";

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getValuationReportsPath() {
        return valuationReportsPath;
    }

    public void setValuationReportsPath(String valuationReportsPath) {
        this.valuationReportsPath = valuationReportsPath;
    }

    /**
     * Builds the full blob path for a valuation report.
     * 
     * @param loanApplicationId The loan application ID
     * @param requestId The request ID
     * @return The full blob path in the format: property-valuation-reports/{loanApplicationId}/{requestId}
     */
    public String buildBlobPath(String loanApplicationId, String requestId) {
        return String.format("%s/%s/%s", valuationReportsPath, loanApplicationId, requestId);
    }
}