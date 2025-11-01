package com.mortgage.valuation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.mortgage.valuation.model.SalesforceTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class SalesforceService {

    private static final Logger logger = LoggerFactory.getLogger(SalesforceService.class);

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final Cache<String, String> tokenCache;

    @Value("${salesforce.login-url}")
    private String loginUrl;
    @Value("${salesforce.client-id}")
    private String clientId;
    @Value("${salesforce.client-secret}")
    private String clientSecret;
    @Value("${salesforce.username}")
    private String username;
    @Value("${salesforce.password}")
    private String password;
    @Value("${salesforce.grant-type}")
    private String grantType;

    public SalesforceService(WebClient webClient, ObjectMapper objectMapper, Cache<String, String> tokenCache) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
        this.tokenCache = tokenCache;
    }

    /**
     * Logs in to Salesforce and caches the token.
     */
    public Mono<String> login() {
        String cachedToken = tokenCache.getIfPresent("access_token");
        if (cachedToken != null) {
            logger.info("Using cached Salesforce access token.");
            return Mono.just(cachedToken);
        }

        return WebClient.create(loginUrl)
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", grantType)
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret)
                        .with("username", username)
                        .with("password", password))
                .retrieve()
                .bodyToMono(SalesforceTokenResponse.class)
                .map(response -> {
                    logger.info("Fetched new Salesforce token. Caching it.");
                    tokenCache.put("access_token", response.getAccessToken());
                    return response.getAccessToken();
                });
    }

    /**
     * Creates a record in the given Salesforce object using the provided Java object.
     *
     * @param objectName Salesforce object name (e.g. CustomObject__c)
     * @param crmRecord  Java object annotated with @JsonProperty for Salesforce fields
     * @return the created record ID (from Salesforce)
     */
    public String createRecord(String objectName, Object crmRecord) {
        var result = login().flatMap(token -> {
            var recordMap = objectMapper.convertValue(crmRecord, Map.class);
            return webClient.post()
                    .uri("/services/data/v57.0/sobjects/{objectName}", objectName)
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(recordMap)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .map(response -> {
                        String id = (String) response.get("id");
                        logger.info(" Created {} record with ID {}", objectName, id);
                        return id;
                    });
        });
        return result.block();
    }

    /**
     * Updates an existing Salesforce record.
     *
     * @param objectName Salesforce object name (e.g. Account, Contact, CustomObject__c)
     * @param recordId   Salesforce record ID (e.g. "0018b00002XYZ123AA")
     * @param crmRecord  Java object annotated with @JsonProperty for Salesforce fields to update
     * @return Mono<Boolean> true if update succeeded, false otherwise
     */
    public boolean updateRecord(String objectName, String recordId, Object crmRecord) {
        var isSuccessful = login().flatMap(token -> {
            var recordMap = objectMapper.convertValue(crmRecord, Map.class);

            return webClient.patch()
                    .uri("/services/data/v60.0/sobjects/{objectName}/{recordId}", objectName, recordId)
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(recordMap)
                    .retrieve()
                    .toBodilessEntity()
                    .map(response -> {
                        logger.info("Updated {} record with ID {}", objectName, recordId);
                        return true;
                    })
                    .onErrorResume(e -> {
                        logger.error("Failed to update Salesforce record {} {}: {}", objectName, recordId, e.getStackTrace());
                        return Mono.just(false);
                    });
        });

        return Boolean.TRUE.equals(isSuccessful.block());
    }

}

