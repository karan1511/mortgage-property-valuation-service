# Troubleshooting Guide

## Common Issues and Solutions

### Issue: "Parameter urlSource or base64Source is required"

**Error:**
```
Status code 400, "{"error":{"code":"InvalidArgument","message":"Invalid argument.","innererror":{"code":"ParameterMissing","message":"The parameter urlSource or base64Source is required."}}}"
```

**Root Cause:** Azure Document Intelligence requires either a URL to the document or base64-encoded content.

**Solution:** The application now correctly passes the binary PDF content to Azure Document Intelligence using the correct API. Make sure you're using the latest code.

**Fixed in:** `PdfTextExtractionService.java`
- Uses `SyncPoller` to handle async operations properly
- Passes BinaryData directly to `beginAnalyzeDocument` with ContentFormat.TEXT
- Waits for completion using `waitForCompletion()`

### Issue: PDF Not Found

**Error:**
```
PDF file not found for request ID: rt74321
```

**Solution:** Ensure your PDF is in the correct location:
```bash
# Check if file exists
ls -la localstack-data/s3/mortgage-valuation-documents/lnap4879/rt74321/report.pdf

# If not, copy your PDF there
mkdir -p localstack-data/s3/mortgage-valuation-documents/lnap4879/rt74321
cp your-valuation-report.pdf localstack-data/s3/mortgage-valuation-documents/lnap4879/rt74321/report.pdf
```

### Issue: Azure Credentials Error

**Error:**
```
Failed to authenticate to Azure services

**Solution:** Verify your credentials in `application.yml` or `.env` file:

```yaml
azure:
  openai:
    endpoint: https://your-endpoint.openai.azure.com/
    api-key: your-api-key
    deployment-name: gpt-4.1
  document-intelligence:
    endpoint: https://your-endpoint.cognitiveservices.azure.com/
    api-key: your-api-key
```

### Issue: "No text content found in PDF"

**Error:**
```
No text content found in PDF
```

**Possible Causes:**
1. PDF might be image-based (scanned) without OCR text layer
2. PDF might be corrupted
3. Azure Document Intelligence might not have processed it correctly

**Solution:**
1. Try with a different PDF that contains text
2. Check if the PDF is readable
3. Check the logs for more details

### Issue: Application Won't Start

**Error:** Port 8080 already in use

**Solution:**
```bash
# Kill the process using port 8080
lsof -ti:8080 | xargs kill

# Or change the port in application.yml
server:
  port: 8081
```

### Issue: Build Errors

**Error:** Gradle build fails

**Solution:**
```bash
# Clean and rebuild
./gradlew clean build

# If gradle wrapper is missing, install gradle or use:
gradle build
```

## Debugging Tips

### Enable Debug Logging

Edit `application.yml`:
```yaml
logging:
  level:
    com.mortgage.valuation: DEBUG
```

### Check Application Logs

```bash
tail -f logs/valuation-app.log
```

### Test Health Endpoint

```bash
curl http://localhost:8080/api/v1/valuation/health
```

### Test PDF Exists Endpoint

```bash
curl http://localhost:8080/api/v1/valuation/exists/rt74321
```

## Getting More Help

1. Check the full stack trace in logs
2. Enable DEBUG logging level
3. Verify all environment variables are set correctly
4. Ensure Azure services are accessible and have quota remaining
5. Check the PDF file is not corrupted

