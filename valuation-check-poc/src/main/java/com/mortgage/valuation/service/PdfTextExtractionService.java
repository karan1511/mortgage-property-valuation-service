package com.mortgage.valuation.service;

import com.azure.ai.documentintelligence.DocumentIntelligenceClient;
import com.azure.ai.documentintelligence.models.AnalyzeDocumentRequest;
import com.azure.ai.documentintelligence.models.AnalyzeResult;
import com.azure.ai.documentintelligence.models.AnalyzeResultOperation;
import com.azure.ai.documentintelligence.models.DocumentPage;
import com.azure.core.util.polling.SyncPoller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;


/**
 * Service for extracting text content from PDF files using Azure Document Intelligence.
 */
@Service
public class PdfTextExtractionService {

    private static final Logger logger = LoggerFactory.getLogger(PdfTextExtractionService.class);

    private final DocumentIntelligenceClient documentIntelligenceClient;

    @Autowired
    public PdfTextExtractionService(DocumentIntelligenceClient documentIntelligenceClient) {
        this.documentIntelligenceClient = documentIntelligenceClient;
    }

    /**
     * Extracts text content from a PDF byte array using Azure Document Intelligence.
     *
     * @param pdfContent The PDF file content as a byte array
     * @return The extracted text content
     * @throws PdfTextExtractionException if text extraction fails
     */
    public String extractText(byte[] pdfContent) throws PdfTextExtractionException {
        if (pdfContent == null || pdfContent.length == 0) {
            throw new PdfTextExtractionException("PDF content is null or empty");
        }

        logger.info("Extracting text from PDF using Azure Document Intelligence: {} bytes", pdfContent.length);

        if (!isPdf(pdfContent)) {
            throw new PdfTextExtractionException("Provided content is not a valid PDF");
        }

        try {
            SyncPoller<AnalyzeResultOperation, AnalyzeResultOperation> analyzeReceiptPoller =
                    documentIntelligenceClient.beginAnalyzeDocument("prebuilt-receipt",
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            new AnalyzeDocumentRequest().setBase64Source(pdfContent));

            AnalyzeResult analyzeResult = analyzeReceiptPoller.getFinalResult().getAnalyzeResult();

            if (analyzeResult == null) {
                logger.error("Analyze result is null");
                throw new PdfTextExtractionException("Failed to analyze document - no result returned");
            }

            // Primary content (may be null for some models/versions)
            String content = analyzeResult.getContent();

            // Fallback: if content is empty, aggregate page lines into a single string
            if (content == null || content.trim().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                if (analyzeResult.getPages() != null) {
                    for (DocumentPage page : analyzeResult.getPages()) {
                        if (page.getLines() != null) {
                            page.getLines().forEach(line -> {
                                if (line.getContent() != null) {
                                    sb.append(line.getContent()).append('\n');
                                }
                            });
                        }
                    }
                }
                content = sb.toString().trim();
            }

            if (content == null || content.isEmpty()) {
                logger.warn("No text content extracted from PDF");
                throw new PdfTextExtractionException("No text content found in PDF");
            }

            logger.info("Successfully extracted text: {} characters", content.length());
            return content.trim();

        } catch (Exception e) {
            logger.error("Error during PDF text extraction with Azure Document Intelligence: {}", e.getMessage(), e);
            throw new PdfTextExtractionException("Failed to extract text from PDF: " + e.getMessage(), e);
        }
    }

    /**
     * Validates if the provided content is a valid PDF.
     *
     * @param content The file content as a byte array
     * @return true if valid PDF, false otherwise
     */
    public boolean isPdf(byte[] content) {
        // Check PDF magic number
        if (content == null || content.length < 4) {
            return false;
        }
        // PDF files start with "%PDF"
        return content[0] == 0x25 && content[1] == 0x50 && content[2] == 0x44 && content[3] == 0x46;
    }

    /**
     * Custom exception for PDF text extraction errors.
     */
    public static class PdfTextExtractionException extends Exception {
        public PdfTextExtractionException(String message) {
            super(message);
        }

        public PdfTextExtractionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}