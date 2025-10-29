package com.mortgage.valuation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Service for reading PDF files from local file system (for local development).
 * Reads from localstack-data directory structure.
 */
@Service
public class S3Service {

    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);
    
    // Base path for local PDF storage (mimics S3 structure)
    private static final String BASE_PATH = "localstack-data/s3/mortgage-valuation-documents";

    /**
     * Downloads a PDF file from local file system based on the request ID.
     *
     * @param requestId The request ID to construct the file path
     * @return The PDF file content as byte array
     * @throws S3ServiceException if the file cannot be read
     */
    public byte[] downloadPdf(String requestId) throws S3ServiceException {
        String filePath = String.format("%s/lnap4879/%s/report.pdf", BASE_PATH, requestId);
        
        logger.info("Reading PDF from local file system: path={}", filePath);

        try {
            Path path = Paths.get(filePath);
            
            if (!Files.exists(path)) {
                logger.error("PDF file not found: {}", filePath);
                throw new S3ServiceException("PDF file not found for request ID: " + requestId);
            }
            
            byte[] pdfContent = Files.readAllBytes(path);
            logger.info("Successfully read PDF from local file system: {} bytes", pdfContent.length);
            
            return pdfContent;

        } catch (IOException e) {
            logger.error("IO error while reading PDF from local file system: {}", e.getMessage(), e);
            throw new S3ServiceException("Failed to read PDF from local file system: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error while reading PDF: {}", e.getMessage(), e);
            throw new S3ServiceException("Unexpected error while reading PDF: " + e.getMessage(), e);
        }
    }

    /**
     * Checks if a PDF file exists in local file system for the given request ID.
     *
     * @param requestId The request ID to check
     * @return true if the file exists, false otherwise
     */
    public boolean pdfExists(String requestId) {
        String filePath = String.format("%s/lnap4879/%s/report.pdf", BASE_PATH, requestId);
        
        try {
            Path path = Paths.get(filePath);
            boolean exists = Files.exists(path);
            logger.debug("Checking if PDF exists: {} = {}", filePath, exists);
            return exists;
        } catch (Exception e) {
            logger.warn("Error checking if PDF exists: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Custom exception for S3 service errors.
     */
    public static class S3ServiceException extends Exception {
        public S3ServiceException(String message) {
            super(message);
        }

        public S3ServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
