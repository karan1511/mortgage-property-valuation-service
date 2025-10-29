# Quick Start Guide

## 🚀 Get Started in 5 Minutes

### Prerequisites Check

- ✅ Java 17+ installed (`java -version`)
- ✅ Docker installed and running (`docker --version`)
- ✅ Azure OpenAI resource created
- ✅ Azure Document Intelligence resource created

### Step 1: Clone and Setup

```bash
git clone <repository-url>
cd valuation-check-poc
```

### Step 2: Configure Azure Credentials

Create `.env` file in the root directory:

```bash
# Azure OpenAI Configuration
AZURE_OPENAI_ENDPOINT=https://your-openai-resource.openai.azure.com/
AZURE_OPENAI_API_KEY=your-openai-api-key
AZURE_OPENAI_DEPLOYMENT_NAME=gpt-4o

# Azure Document Intelligence Configuration
AZURE_DOCUMENT_INTELLIGENCE_ENDPOINT=https://your-document-intelligence-resource.cognitiveservices.azure.com/
AZURE_DOCUMENT_INTELLIGENCE_API_KEY=your-document-intelligence-api-key
```

### Step 3: Create Directory and Add PDF

```bash
# Create the directory structure
mkdir -p localstack-data/s3/mortgage-valuation-documents/lnap4879/rt74321

# Copy your PDF file
cp your-valuation-report.pdf localstack-data/s3/mortgage-valuation-documents/lnap4879/rt74321/report.pdf
```

**Verify:**
```bash
ls -la localstack-data/s3/mortgage-valuation-documents/lnap4879/rt74321/report.pdf
```

### Step 5: Start the Application

```bash
# Load environment variables
source .env  # or: export $(cat .env | xargs)

# Build and run
./gradlew bootRun
```

### Step 6: Test the API

```bash
# Health check
curl http://localhost:8080/api/v1/valuation/health

# Process valuation
curl -X POST http://localhost:8080/api/v1/valuation/process \
  -H "Content-Type: application/json" \
  -d '{"requestId": "rt74321"}'
```

## ✅ Success!

You should see a structured JSON response with property valuation details.

## 🔧 Common Issues

### PDF Not Found
```bash
# Check if file exists
ls -la localstack-data/s3/mortgage-valuation-documents/lnap4879/rt74321/report.pdf

# Re-copy if needed
cp your-file.pdf localstack-data/s3/mortgage-valuation-documents/lnap4879/rt74321/report.pdf
```

### Azure Credentials Error
```bash
# Check environment variables
echo $AZURE_OPENAI_ENDPOINT
echo $AZURE_DOCUMENT_INTELLIGENCE_ENDPOINT

# Reload .env file
source .env
```

### Port Already in Use
```bash
# Change port in application.yml or kill the process
lsof -ti:8080 | xargs kill
```

## 📚 Need More Help?

- See `README.md` for detailed documentation
- Check logs: `tail -f logs/valuation-app.log`
- Verify LocalStack: `docker-compose ps`
