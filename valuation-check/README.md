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

| Component      | Technology                  |
|----------------|-----------------------------|
| Language       | Java 17                     |
| Framework      | Spring Boot 3.x             |
| Build Tool     | Gradle 8.5                  |
| PDF Storage    | Azure Storage Account       |
| PDF Processing | Azure Document Intelligence |
| AI/LLM         | Azure OpenAI (GPT-4o)       |
| Logging        | SLF4J + Logback             |