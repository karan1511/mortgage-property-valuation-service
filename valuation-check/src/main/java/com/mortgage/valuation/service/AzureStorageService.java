package com.mortgage.valuation.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.ListBlobsOptions;
import com.mortgage.valuation.config.AzureStorageConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Service for interacting with Azure Blob Storage to fetch valuation report PDFs.
 */
@Service
public class AzureStorageService {

    private static final Logger logger = LoggerFactory.getLogger(AzureStorageService.class);

    private final BlobContainerClient containerClient;
    private final AzureStorageConfig config;

    @Autowired
    public AzureStorageService(AzureStorageConfig config) {
        this.config = config;

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(config.getConnectionString())
                .buildClient();

        this.containerClient = blobServiceClient.getBlobContainerClient(config.getContainerName());
    }

    /**
     * Uploads a multipart file to Azure Data Lake Storage / Blob Storage.
     *
     * @param loanApplicationId The loan application ID
     * @param requestId         The request ID
     * @param file              The multipart file from the request
     * @return The full blob URL after upload
     * @throws IOException if the upload fails
     */
    public String uploadFile(String loanApplicationId, String requestId, MultipartFile file) throws IOException {
        String blobPath = config.buildBlobPath(loanApplicationId, requestId);
        String fileName = file.getOriginalFilename();
        String fullBlobPath = blobPath.endsWith("/") ? blobPath + fileName : blobPath + "/" + fileName;

        logger.info("Uploading file '{}' to Azure Storage path: {}", fileName, fullBlobPath);

        try {
            BlobClient blobClient = containerClient.getBlobClient(fullBlobPath);

            try (InputStream inputStream = file.getInputStream()) {
                blobClient.upload(inputStream, file.getSize(), true); // true = overwrite existing
            }

            String blobUrl = blobClient.getBlobUrl();
            logger.info("File '{}' uploaded successfully to: {}", fileName, blobUrl);
            return blobUrl;

        } catch (Exception e) {
            logger.error("Error uploading file to Azure Storage: {}", e.getMessage(), e);
            throw new IOException("Failed to upload file: " + e.getMessage(), e);
        }
    }


    /**
     * Fetches a valuation report PDF from Azure Storage.
     *
     * @param loanApplicationId The loan application ID
     * @param requestId         The request ID
     * @return The PDF content as a byte array
     * @throws IOException if there's an error reading the blob
     */
    public byte[] fetchValuationReport(String loanApplicationId, String requestId) throws IOException {
        String blobPath = config.buildBlobPath(loanApplicationId, requestId);
        logger.info("Fetching valuation report from Azure Storage: {}", blobPath);
        // First try exact path (if the blob was uploaded with the requestId as the name)
        try {

            // search for any blob under the folder prefix
            String prefix = blobPath.endsWith("/") ? blobPath : blobPath + "/";
            String blobToDownload = null;

            for (BlobItem item : containerClient.listBlobs(new ListBlobsOptions().setPrefix(prefix), null)) {
                String name = item.getName();
                if (name.toLowerCase().endsWith(".pdf")) {
                    blobToDownload = name;
                    break;
                }
            }

            if (blobToDownload == null) {
                logger.error("Valuation report not found in Azure Storage: {}", blobPath);
                throw new IOException("Valuation report not found: " + blobPath);
            }

            logger.info("Found blob to download: {}", blobToDownload);
            BlobClient blobClient = containerClient.getBlobClient(blobToDownload);
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                blobClient.downloadStream(outputStream);
                return outputStream.toByteArray();
            }

        } catch (Exception e) {
            logger.error("Error downloading valuation report from Azure Storage: {}", e.getMessage(), e);
            throw new IOException("Failed to download valuation report: " + e.getMessage(), e);
        }
    }
}