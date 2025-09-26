# Use a lightweight OpenJDK base image
FROM openjdk:17-jdk-slim

# Add a volume for temporary files (optional)
VOLUME /tmp

WORKDIR /app

# Copy the jar from your local target directory to the image
COPY target/user-service.jar app.jar

# Expose the port your Spring Boot app runs on (default 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]

docker build -t user-service:1.0.0 .
docker run -p 8080:8080 user-service:1.0.0
