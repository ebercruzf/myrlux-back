# Use OpenJDK 21 as base image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the specific JAR file
COPY target/myrlux-back-0.0.1.jar app.jar

# Expose the application port
EXPOSE 11002

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=dev
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/schoolbd

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]