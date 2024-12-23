# Use OpenJDK as the base image
FROM openjdk:17-jdk-alpine

# Set working directory in container
WORKDIR /app

# Copy Gradle wrapper and project files
COPY . /app

# Build the application
RUN ./gradlew --no-daemon clean build -x test

# Expose port 8080 for the API
EXPOSE 8080

# Run the application
CMD ["sh", "-c", "java -jar build/libs/*.jar"]
