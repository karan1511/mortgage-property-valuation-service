package com.mortgage.valuation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main Spring Boot application for mortgage property valuation processing.
 * This application provides a REST API endpoint that:
 * 1. Accepts a requestId
 * 2. Downloads valuation PDF from S3
 * 3. Extracts text using Apache Tika
 * 4. Processes with Azure OpenAI
 * 5. Returns structured JSON response
 */
@SpringBootApplication
@EnableAsync
public class ValuationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValuationApplication.class, args);
    }
}
