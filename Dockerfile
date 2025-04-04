# Base image with JDK 17 --> SDK --> JVM
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the .env file(s) from the root of the project to the working directory
COPY .env .env

# Expose the application port
EXPOSE 8080

# Copy the JAR file from the root of the project to the working directory
COPY target/academic-1.0_PRERELEASE.jar unamba.jar

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "unamba.jar"]