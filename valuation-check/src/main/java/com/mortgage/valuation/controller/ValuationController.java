package com.mortgage.valuation.controller;

import com.mortgage.valuation.model.ErrorResponse;
import com.mortgage.valuation.model.ValuationRequest;
import com.mortgage.valuation.model.ValuationResponse;
import com.mortgage.valuation.service.AzureOpenAIService;
import com.mortgage.valuation.service.PdfTextExtractionService;
import com.mortgage.valuation.service.AzureStorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for property valuation processing.
 */
@RestController
@RequestMapping("/valuation")
@CrossOrigin(origins = "http://localhost:5173")
public class ValuationController {

    private static final Logger logger = LoggerFactory.getLogger(ValuationController.class);

    private final AzureStorageService azureStorageService;
    private final PdfTextExtractionService pdfTextExtractionService;
    private final AzureOpenAIService azureOpenAIService;

    @Autowired
    public ValuationController(AzureStorageService azureStorageService,
                             PdfTextExtractionService pdfTextExtractionService,
                             AzureOpenAIService azureOpenAIService) {
        this.azureStorageService = azureStorageService;
        this.pdfTextExtractionService = pdfTextExtractionService;
        this.azureOpenAIService = azureOpenAIService;
    }

    /**
     * Processes a property valuation request.
     * 
     * @param request The valuation request containing requestId
     * @param httpRequest The HTTP request for error context
     * @return Structured valuation response
     */
    @PostMapping("/process")
    public ResponseEntity<?> processValuation(@Valid @RequestBody ValuationRequest request, 
                                            HttpServletRequest httpRequest) {
        String requestId = request.getRequestId();
        String loanApplicationId = request.getLoanApplicationId();
        logger.info("Processing valuation request: {} for loan application: {}", requestId, loanApplicationId);

        try {
            // Step 1: Download PDF from Azure Storage
            logger.info("Step 1: Downloading PDF from Azure Storage for request: {} and loan application: {}", 
                requestId, loanApplicationId);
            byte[] pdfContent = azureStorageService.fetchValuationReport(loanApplicationId, requestId);

            // Step 2: Validate PDF content
            if (!pdfTextExtractionService.isPdf(pdfContent)) {
                logger.error("Downloaded file is not a valid PDF for request: {}", requestId);
                return createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid PDF file",
                    "The file downloaded from S3 is not a valid PDF",
                    httpRequest.getRequestURI(), requestId);
            }

            // Step 3: Extract text from PDF
            logger.info("Step 2: Extracting text from PDF for request: {}", requestId);
            String extractedText = pdfTextExtractionService.extractText(pdfContent);

            if (extractedText.trim().isEmpty()) {
                logger.error("No text content extracted from PDF for request: {}", requestId);
                return createErrorResponse(HttpStatus.BAD_REQUEST, "Empty PDF content",
                    "No text content could be extracted from the PDF",
                    httpRequest.getRequestURI(), requestId);
            }

            // Step 4: Process with Azure OpenAI
            logger.info("Step 3: Processing text with Azure OpenAI for request: {}", requestId);
            ValuationResponse valuationResponse = azureOpenAIService.processValuationText(extractedText, requestId);

            logger.info("Successfully processed valuation request: {}", requestId);
            return ResponseEntity.ok(valuationResponse);

        } catch (IOException e) {
            logger.error("Azure Storage error processing request {}: {}", requestId, e.getMessage());
            return createErrorResponse(HttpStatus.NOT_FOUND, "PDF not found", 
                e.getMessage(), httpRequest.getRequestURI(), requestId);
                
        } catch (PdfTextExtractionService.PdfTextExtractionException e) {
            logger.error("PDF extraction error processing request {}: {}", requestId, e.getMessage());
            return createErrorResponse(HttpStatus.BAD_REQUEST, "PDF processing failed", 
                e.getMessage(), httpRequest.getRequestURI(), requestId);
                
        } catch (AzureOpenAIService.AzureOpenAIServiceException e) {
            logger.error("Azure OpenAI error processing request {}: {}", requestId, e.getMessage());
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "AI processing failed", 
                e.getMessage(), httpRequest.getRequestURI(), requestId);
                
        } catch (Exception e) {
            logger.error("Unexpected error processing request {}: {}", requestId, e.getMessage(), e);
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", 
                "An unexpected error occurred while processing the request", 
                httpRequest.getRequestURI(), requestId);
        }
    }

    /**
     * Checks if a PDF exists for the given request ID.
     * 
     * @param requestId The request ID to check
     * @return Existence status
     */
//    @GetMapping("/exists/{requestId}")
//    public ResponseEntity<Object> checkPdfExists(@PathVariable String requestId) {
//        logger.info("Checking if PDF exists for request: {}", requestId);
//
//        try {
//            boolean exists = s3Service.pdfExists(requestId);
//            return ResponseEntity.ok().body(new Object() {
//                public final String requestId = requestId;
//                public final boolean exists = exists;
//                public final long timestamp = System.currentTimeMillis();
//            });
//        } catch (Exception e) {
//            logger.error("Error checking PDF existence for request {}: {}", requestId, e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new Object() {
//                    public final String requestId = requestId;
//                    public final boolean exists = false;
//                    public final String error = e.getMessage();
//                    public final long timestamp = System.currentTimeMillis();
//                });
//        }
//    }

    private ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus status, String error,
                                                              String message, String path, String requestId) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), error, message, path, requestId);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
