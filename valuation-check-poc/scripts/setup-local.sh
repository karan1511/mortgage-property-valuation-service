#!/bin/bash

# Setup script for local development environment

set -e

echo "🏠 Setting up Mortgage Valuation Application for local development..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker and try again."
    exit 1
fi

# Check if Java 17 is installed
if ! java -version 2>&1 | grep -q "17"; then
    echo "❌ Java 17 is required. Please install Java 17 and try again."
    exit 1
fi

# Check for .env file
if [ ! -f .env ]; then
    echo "📝 Creating .env file from template..."
    cat > .env << EOF
# Azure OpenAI Configuration
AZURE_OPENAI_ENDPOINT=https://your-openai-resource.openai.azure.com/
AZURE_OPENAI_API_KEY=your-openai-api-key
AZURE_OPENAI_DEPLOYMENT_NAME=gpt-4o

# Azure Document Intelligence Configuration
AZURE_DOCUMENT_INTELLIGENCE_ENDPOINT=https://your-document-intelligence-resource.cognitiveservices.azure.com/
AZURE_DOCUMENT_INTELLIGENCE_API_KEY=your-document-intelligence-api-key
EOF
    echo "⚠️  Please update .env file with your actual Azure credentials before proceeding"
    echo ""
    echo "Press Enter to continue after updating .env..."
    read
fi

# Create necessary directories
echo "📁 Creating necessary directories..."
mkdir -p logs
mkdir -p localstack-data

# Start LocalStack for S3
echo "🚀 Starting LocalStack (S3)..."
docker-compose up -d localstack

# Wait for LocalStack to be ready
echo "⏳ Waiting for LocalStack to be ready..."
sleep 15

# Create S3 bucket
echo "🪣 Creating S3 bucket..."
docker-compose up setup-s3

# Create the directory structure in LocalStack
echo "📁 Creating directory structure for PDFs..."
mkdir -p localstack-data/s3/mortgage-valuation-documents/lnap4879/rt74321

echo ""
echo "✅ LocalStack setup complete!"
echo ""
echo "📋 Next steps:"
echo "1. Place your PDF file at: localstack-data/s3/mortgage-valuation-documents/lnap4879/rt74321/report.pdf"
echo "2. Or upload using AWS CLI:"
echo "   aws --endpoint-url=http://localhost:4566 s3 cp your-file.pdf \\"
echo "     s3://mortgage-valuation-documents/lnap4879/rt74321/report.pdf"
echo ""
echo "🚀 To start the application, run:"
echo "   ./gradlew bootRun"
echo ""
echo "🧪 To test the API:"
echo "   curl -X POST http://localhost:8080/api/v1/valuation/process \\"
echo "     -H 'Content-Type: application/json' \\"
echo "     -d '{\"requestId\": \"rt74321\"}'"