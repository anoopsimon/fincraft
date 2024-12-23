# ---------- STAGE 1: BUILD WITH GRADLE ----------
FROM gradle:7.6.2-jdk17-alpine AS builder
WORKDIR /app

# Copy Gradle wrapper and config first (for caching)
COPY gradlew settings.gradle build.gradle ./
COPY gradle gradle

# Copy source code
COPY src src

# Run the Gradle build inside the container
RUN ./gradlew clean bootJar

# ---------- STAGE 2: RUN THE SPRING BOOT APP ----------
FROM openjdk:17-alpine
WORKDIR /app

# Copy the generated fat JAR (Spring Boot executable JAR) from builder
COPY --from=builder /app/build/libs/fincraft-0.0.1-SNAPSHOT.jar app.jar

# Expose the port (this is optional/documentation)
EXPOSE 8080

# Start Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
