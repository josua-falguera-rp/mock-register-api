# Build stage
FROM gradle:9.2.1-jdk21 AS build
WORKDIR /app

# Copy gradle files first for better layer caching
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Download dependencies (cached if build.gradle hasn't changed)
RUN gradle dependencies --no-daemon

# Copy source code
COPY src ./src

# Build the application
RUN gradle bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the built JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]