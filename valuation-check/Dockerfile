FROM gradle:8.4-jdk17 AS builder
WORKDIR /app

# Copy wrapper + configs
COPY gradlew /app/
COPY gradle /app/gradle
COPY build.gradle settings.gradle /app/

# Give execution permission (especially on Linux)
RUN chmod +x gradlew

# Download dependencies
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY . /app

# Build the JAR
RUN ./gradlew bootJar --no-daemon

# Stage 2: Create minimal runtime image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy JAR file from build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port your Spring Boot app uses (default is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]