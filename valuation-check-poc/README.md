# Mortgage Valuation Application

A Spring Boot application for processing UK property valuation reports using Azure Document Intelligence for PDF extraction and Azure OpenAI for structured data generation.

**✨ Simplified for Local Development** - No AWS credentials required! The application reads PDFs directly from your local file system.

## 🏗️ Architecture

```
┌─────────────────┐    ┌──────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   REST API      │───▶│  LocalStack  │───▶│  Azure Doc      │───▶│  Azure OpenAI   │
│  (Spring Boot)  │    │  (S3 Emulator)│    │  Intelligence   │    │    (GPT-4o)     │
└─────────────────┘    └──────────────┘    └─────────────────┘    └─────────────────┘
         │                     │                     │                     │
         │                     │                     │                     │
    GET request         Download PDF          Extract text         Generate JSON
```

## 🛠️ Technology Stack

| Component | Technology |
|-----------|------------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Build Tool | Gradle 8.5 |
| PDF Processing | Azure Document Intelligence |
| AI/LLM | Azure OpenAI (GPT-4o) |
| S3 Emulator | LocalStack |
| Logging | SLF4J + Logback |

## 📋 Prerequisites

- **Java 17+** installed
- **Docker** and **Docker Compose** installed
- **Azure OpenAI** resource with API access
- **Azure Document Intelligence** resource with API access
- **Gradle** (or use the included Gradle wrapper)

## 🚀 Quick Start

### Step 1: Clone the Repository

```bash
git clone <repository-url>
cd valuation-check-poc
```

### Step 2: Set Up Azure Services

You need to create two Azure resources:

1. **Azure OpenAI Resource**
   - Go to Azure Portal → Create Azure OpenAI resource
   - Deploy a model (GPT-4o recommended)
   - Note down the endpoint and API key

2. **Azure Document Intelligence Resource**
   - Go to Azure Portal → Create Document Intelligence resource
   - Note down the endpoint and API key

### Step 3: Configure Environment Variables

Create a `.env` file in the root directory:

```bash
# Azure OpenAI Configuration
AZURE_OPENAI_ENDPOINT=https://your-openai-resource.openai.azure.com/
AZURE_OPENAI_API_KEY=your-openai-api-key
AZURE_OPENAI_DEPLOYMENT_NAME=gpt-4o

# Azure Document Intelligence Configuration
AZURE_DOCUMENT_INTELLIGENCE_ENDPOINT=https://your-document-intelligence-resource.cognitiveservices.azure.com/
AZURE_DOCUMENT_INTELLIGENCE_API_KEY=your-document-intelligence-api-key
```

### Step 4: Create Directory Structure and Add PDF

**✨ No AWS or LocalStack required!** The application reads PDFs directly from your local file system.

Create the directory structure in your project:

```bash
# Create the directory structure
mkdir -p localstack-data/s3/mortgage-valuation-documents/lnap4879/rt74321

# Copy your PDF file to this directory
cp your-valuation-report.pdf localstack-data/s3/mortgage-valuation-documents/lnap4879/rt74321/report.pdf
```

The application expects PDFs in the path: `localstack-data/s3/mortgage-valuation-documents/lnap4879/{requestId}/report.pdf`

**Verify the file is in place:**

```bash
# Check if file exists
ls -la localstack-data/s3/mortgage-valuation-documents/lnap4879/rt74321/report.pdf
```

**Create a Sample PDF (Optional)**

```bash
# Install tools to create a test PDF
# macOS: brew install wkhtmltopdf
# Ubuntu: sudo apt-get install wkhtmltopdf

# Create sample PDF
./scripts/create-test-pdf.sh
```

### Step 6: Build and Run the Application

**Option A: Using Gradle**

```bash
# Build the application
./gradlew build -x test

# Run the application
./gradlew bootRun
```

**Option B: Using Docker**

```bash
# Build Docker image
docker build -t mortgage-valuation-app .

# Run the application
docker run -p 8080:8080 \
  -e AZURE_OPENAI_ENDPOINT=$AZURE_OPENAI_ENDPOINT \
  -e AZURE_OPENAI_API_KEY=$AZURE_OPENAI_API_KEY \
  -e AZURE_OPENAI_DEPLOYMENT_NAME=$AZURE_OPENAI_DEPLOYMENT_NAME \
  -e AZURE_DOCUMENT_INTELLIGENCE_ENDPOINT=$AZURE_DOCUMENT_INTELLIGENCE_ENDPOINT \
  -e AZURE_DOCUMENT_INTELLIGENCE_API_KEY=$AZURE_DOCUMENT_INTELLIGENCE_API_KEY \
  -e AWS_S3_ENDPOINT=http://host.docker.internal:4566 \
  mortgage-valuation-app
```

### Step 7: Test the API

```bash
# Health check
curl http://localhost:8080/api/v1/valuation/health

# Check if PDF exists
curl http://localhost:8080/api/v1/valuation/exists/rt74321

# Process a valuation (replace rt74321 with your requestId)
curl -X POST http://localhost:8080/api/v1/valuation/process \
  -H "Content-Type: application/json" \
  -d '{"requestId": "rt74321"}'
```

## 📚 API Documentation

### Endpoints

#### 1. POST `/api/v1/valuation/process`

Process a property valuation request.

**Request:**
```json
{
  "requestId": "rt74321"
}
```

**Response:**
```json
{
  "requestId": "rt74321",
  "propertyAddress": {
    "line1": "123 Main Street",
    "line2": "Apartment 4B",
    "city": "London",
    "postcode": "SW1A 1AA",
    "county": "Greater London"
  },
  "valuationDetails": {
    "estimatedValue": 750000.00,
    "valuationRange": {
      "minValue": 700000.00,
      "maxValue": 800000.00
    },
    "valuationMethod": "Comparable Sales",
    "valuationDate": "2024-01-15"
  },
  "propertyCharacteristics": {
    "propertyType": "Apartment",
    "bedrooms": 2,
    "bathrooms": 2,
    "receptionRooms": 1,
    "totalFloorArea": 85.5,
    "yearBuilt": 2015,
    "condition": "Good"
  },
  "marketAnalysis": {
    "localMarketTrend": "Stable",
    "averagePricePerSqFt": 8500.00,
    "comparableProperties": [...]
  },
  "riskAssessment": {
    "overallRisk": "Low",
    "riskFactors": [],
    "riskScore": 2
  },
  "recommendations": [
    "Property is suitable for mortgage lending"
  ],
  "processedAt": "2024-01-15T10:30:00",
  "confidenceScore": 0.92
}
```

#### 2. GET `/api/v1/valuation/health`

Health check endpoint.

**Response:**
```json
{
  "status": "UP",
  "service": "mortgage-valuation-app",
  "timestamp": 1705312200000
}
```

#### 3. GET `/api/v1/valuation/exists/{requestId}`

Check if a PDF exists for the given request ID.

**Response:**
```json
{
  "requestId": "rt74321",
  "exists": true,
  "timestamp": 1705312200000
}
```

## 🔧 Configuration

### Application Properties

Edit `src/main/resources/application.yml` for configuration:

```yaml
server:
  port: 8080
  servlet:
    context-path: /api/v1

# AWS S3 Configuration (LocalStack)
aws:
  s3:
    bucket-name: mortgage-valuation-documents
    region: eu-west-2
    endpoint: ${AWS_S3_ENDPOINT:http://localhost:4566}

# Azure Configuration
azure:
  openai:
    endpoint: ${AZURE_OPENAI_ENDPOINT}
    api-key: ${AZURE_OPENAI_API_KEY}
    deployment-name: ${AZURE_OPENAI_DEPLOYMENT_NAME}
    max-tokens: 4000
    temperature: 0.1
  document-intelligence:
    endpoint: ${AZURE_DOCUMENT_INTELLIGENCE_ENDPOINT}
    api-key: ${AZURE_DOCUMENT_INTELLIGENCE_API_KEY}
```

## 📁 Project Structure

```
valuation-check-poc/
├── src/main/java/com/mortgage/valuation/
│   ├── ValuationApplication.java          # Main application
│   ├── config/                           # Configuration classes
│   │   ├── AwsConfig.java
│   │   ├── AzureDocumentIntelligenceConfig.java
│   │   └── AzureOpenAIConfig.java
│   ├── controller/                       # REST controllers
│   │   └── ValuationController.java
│   ├── dto/                             # Data Transfer Objects
│   │   ├── ValuationRequest.java
│   │   ├── ValuationResponse.java
│   │   └── ErrorResponse.java
│   ├── service/                         # Business logic
│   │   ├── S3Service.java
│   │   ├── PdfTextExtractionService.java
│   │   └── AzureOpenAIService.java
│   └── exception/                       # Exception handling
│       └── GlobalExceptionHandler.java
├── src/main/resources/
│   └── application.yml                  # Configuration
├── src/test/                           # Test files
├── scripts/                            # Utility scripts
│   ├── setup-local.sh
│   └── create-test-pdf.sh
├── docker-compose.yml                  # LocalStack setup
├── Dockerfile                          # Container config
├── build.gradle                        # Build config
└── README.md                           # This file
```

## 🧪 Testing

### Run Unit Tests

```bash
./gradlew test
```

### Test API with Sample Requests

Use the provided `sample-requests.http` file or test manually:

```bash
# Test health endpoint
curl http://localhost:8080/api/v1/valuation/health

# Test valuation processing
curl -X POST http://localhost:8080/api/v1/valuation/process \
  -H "Content-Type: application/json" \
  -d '{"requestId": "rt74321"}'
```

## 🔍 Troubleshooting

### Issue: PDF not found

**Solution:** Ensure your PDF is uploaded to the correct path in LocalStack:
```bash
# Check if file exists
aws --endpoint-url=http://localhost:4566 s3 ls s3://mortgage-valuation-documents/lnap4879/rt74321/

# Upload file if missing
aws --endpoint-url=http://localhost:4566 s3 cp report.pdf s3://mortgage-valuation-documents/lnap4879/rt74321/report.pdf
```

### Issue: Azure services not working

**Solution:** Verify your Azure credentials:
```bash
# Check environment variables
echo $AZURE_OPENAI_ENDPOINT
echo $AZURE_OPENAI_API_KEY
echo $AZURE_DOCUMENT_INTELLIGENCE_ENDPOINT
echo $AZURE_DOCUMENT_INTELLIGENCE_API_KEY
```


### Issue: Port already in use

**Solution:** Change the port in `application.yml`:
```yaml
server:
  port: 8081  # Use a different port
```

## 📊 Monitoring

### View Logs

Application logs are written to:
- **Console**: Real-time logs
- **File**: `logs/valuation-app.log` (rotated daily)

### Health Checks

- **Application Health**: `GET /api/v1/valuation/health`
- **Actuator Endpoints**: `GET /actuator/health`, `GET /actuator/info`

## 🆘 Support

For issues and questions:
1. Check the logs in `logs/valuation-app.log`
2. Review the troubleshooting section above
3. Verify Azure service credentials
4. Ensure PDF file is in the correct location

## 📝 Notes

- This is a local development setup that reads PDFs from the local file system
- For production deployment, configure actual AWS S3 credentials by uncommenting the AWS config in `AwsConfig.java`
- Ensure your Azure resources have sufficient quota
- The application uses Azure Document Intelligence for PDF text extraction
- Make sure your PDF files are valid and readable

## 🔄 Workflow

1. Place PDF in local directory: `localstack-data/s3/mortgage-valuation-documents/lnap4879/{requestId}/report.pdf`
2. Application receives request with `requestId`
3. Application reads PDF from local file system
4. Azure Document Intelligence extracts text from PDF
5. Azure OpenAI processes extracted text to generate structured JSON
6. Application returns structured valuation response

---

**Version:** 1.0.0  
**Last Updated:** 2024