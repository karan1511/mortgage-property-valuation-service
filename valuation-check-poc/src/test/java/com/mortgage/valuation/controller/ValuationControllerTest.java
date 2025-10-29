package com.mortgage.valuation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mortgage.valuation.model.ValuationRequest;
import com.mortgage.valuation.service.AzureOpenAIService;
import com.mortgage.valuation.service.PdfTextExtractionService;
import com.mortgage.valuation.service.S3Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ValuationController.class)
class ValuationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private S3Service s3Service;

    @MockBean
    private PdfTextExtractionService pdfTextExtractionService;

    @MockBean
    private AzureOpenAIService azureOpenAIService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/valuation/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("mortgage-valuation-app"));
    }

    @Test
    void testCheckPdfExists() throws Exception {
        when(s3Service.pdfExists("rt74321")).thenReturn(true);

        mockMvc.perform(get("/api/v1/valuation/exists/rt74321"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").value("rt74321"))
                .andExpect(jsonPath("$.exists").value(true));
    }

    @Test
    void testProcessValuationSuccess() throws Exception {
        // Mock PDF content
        byte[] pdfContent = "Sample PDF content".getBytes();
        when(s3Service.downloadPdf("rt74321")).thenReturn(pdfContent);
        when(pdfTextExtractionService.isPdf(pdfContent)).thenReturn(true);
        when(pdfTextExtractionService.extractText(pdfContent)).thenReturn("Sample extracted text");

        // Mock AI response
        com.mortgage.valuation.model.ValuationResponse mockResponse = new com.mortgage.valuation.model.ValuationResponse("rt74321");
        when(azureOpenAIService.processValuationText(anyString(), anyString())).thenReturn(mockResponse);

        ValuationRequest request = new ValuationRequest("rt74321");

        mockMvc.perform(post("/api/v1/valuation/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requestId").value("rt74321"));
    }

    @Test
    void testProcessValuationWithInvalidRequest() throws Exception {
        ValuationRequest request = new ValuationRequest("");

        mockMvc.perform(post("/api/v1/valuation/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testProcessValuationWithPdfNotFound() throws Exception {
        when(s3Service.downloadPdf("rt74321")).thenThrow(new S3Service.S3ServiceException("PDF not found"));

        ValuationRequest request = new ValuationRequest("rt74321");

        mockMvc.perform(post("/api/v1/valuation/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("PDF not found"));
    }
}
