FROM maven:3.9.6-amazoncorretto-21 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

# Use a lightweight OpenJDK base image
FROM openjdk:21-jdk-slim
# Copy the jar from your local target directory to the image
COPY --from=build /app/target/user-service.jar /app/user-service.jar
# Expose the port your Spring Boot app runs on (default 7070)
EXPOSE 7070
# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]

#docker build -t user-service:1.0.0 .
#docker run -p 7070:7070 user-service:1.0.0