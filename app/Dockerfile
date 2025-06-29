# Stage 1: Build the application using a full JDK
# Use an official JDK 21 base image
# FROM eclipse-temurin:21-jdk as builder
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# # Set working directory inside the container to /app
 WORKDIR /app

# Copy Maven files
COPY . .

# Package the application using Maven (no tests)
# Run Maven to build the app
RUN mvn clean package -DskipTests
# This will create a jar file in the target directory, e.g., target/timesheet-ci-1.0.0.jar


# Debug: list files to verify the JAR is created
RUN ls -l /app/target


# Stage 2: Create a lightweight runtime image
# ---- Production stage ----
FROM eclipse-temurin:17-jre

# Create app directory
WORKDIR /app

COPY --from=builder /app/target/*-ci-*.jar app.jar

# Copy the jar file from the builder stage
COPY --from=builder /app/target/timesheet-ci-*.jar app.jar

# Expose port (Spring Boot default is 8080)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
