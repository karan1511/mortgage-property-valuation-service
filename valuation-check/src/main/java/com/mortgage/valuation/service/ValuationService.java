package com.mortgage.valuation.service;

import com.mortgage.valuation.exception.InvalidFileTypeException;
import com.mortgage.valuation.mapper.ValuationMapper;
import com.mortgage.valuation.model.FileUploadRequest;
import com.mortgage.valuation.model.ValuationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import static com.mortgage.valuation.mapper.ValuationMapper.buildCVRecord;

@Service
public class ValuationService {

    private static final Logger logger = LoggerFactory.getLogger(ValuationService.class);

    private final AzureStorageService azureStorageService;
    private final PdfTextExtractionService pdfTextExtractionService;
    private final AzureOpenAIService azureOpenAIService;
    private final SalesforceService salesforceService;
    private final ValuationMapper valuationMapper;

    public ValuationService(AzureStorageService azureStorageService, PdfTextExtractionService pdfTextExtractionService, AzureOpenAIService azureOpenAIService, SalesforceService salesforceService, ValuationMapper valuationMapper) {
        this.azureStorageService = azureStorageService;
        this.pdfTextExtractionService = pdfTextExtractionService;
        this.azureOpenAIService = azureOpenAIService;
        this.salesforceService = salesforceService;
        this.valuationMapper = valuationMapper;
    }

    public String createRecord(FileUploadRequest request) {
        logger.info("Starting creation of Valuation record for Loan Id: {}", request.getLoanApplicationId());
        var id = salesforceService.createRecord("LLC_BI__Collateral_Valuation__c", buildCVRecord(request));
        logger.info("Collateral Valuation record created with Id: {}", id);
        return id;
    }

    // @Async
    public String uploadFile(MultipartFile file, FileUploadRequest request) throws IOException {
        String loanId = request.getLoanApplicationId();
        logger.atInfo().log("Starting upload for Loan Id {}", loanId);

        try {
            String url = azureStorageService.uploadFile(loanId, request.getRequestId(), file);
            logger.atInfo().log("Successfully uploaded file for Loan Id {}", loanId);
            return url;
        } catch (IOException e) {
            logger.atError().log("IOException while uploading file for Loan Id {}: {}", loanId, e.getMessage(), e);
            throw e;
        }
    }

    @Async
    public void processFileAndUpdateCRM(MultipartFile file, String cvId, FileUploadRequest request) throws IOException, PdfTextExtractionService.PdfTextExtractionException, AzureOpenAIService.AzureOpenAIServiceException {

        String requestId = request.getLoanApplicationId(); //TODO - requestId purpose here?
        logger.info("Processing valuation request for loan application: {}", requestId);
            byte[] pdfContent = file.getBytes();

            // Step 1: Validate PDF content
            logger.info("Step 1: Validating PDF content for request: {}", requestId);
            if (!pdfTextExtractionService.isPdf(pdfContent)) {
                logger.error("File is not a valid PDF for request: {}", requestId);
                throw new InvalidFileTypeException("File Type must be pdf");
            }

            // Step 2: Extract text from PDF
            logger.info("Step 2: Extracting text from PDF for request: {}", requestId);
            String extractedText = pdfTextExtractionService.extractText(pdfContent);
            if (extractedText.trim().isEmpty()) {
                logger.error("No text content extracted from PDF for request: {}", requestId);
                throw new InvalidFileTypeException("File must not be empty");
            }

            // Step 3: Process with Azure OpenAI
            logger.info("Step 3: Processing text with Azure OpenAI for request: {}", requestId);
            ValuationResponse valuationResponse = azureOpenAIService.processValuationText(extractedText, requestId);

            // Step 4: Update CRM record with Valuation details
            logger.info("Step 4: Update CRM record with Valuation details for request: {}", requestId);
            var cvRecord = valuationMapper.convertToCVRecord(valuationResponse);
            salesforceService.updateRecord("LLC_BI__Collateral_Valuation__c", cvId, cvRecord);

            logger.info("Successfully processed valuation request: {}", requestId);
    }

}
