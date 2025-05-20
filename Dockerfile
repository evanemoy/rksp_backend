# Build stage
FROM gradle:8.4.0-jdk17 AS build
WORKDIR /app

# Copy Gradle files first to leverage layer caching
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
RUN gradle dependencies || return 0

# Copy application source
COPY src ./src

# Build the application
RUN gradle clean build --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Set environment variables
# ENV SPRING_PROFILES_ACTIVE=prod
# ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
ENV DB_HOST="dpg-d0k5o63e5dus73bg10ig-a"
ENV DB_PORT="5432"
ENV DB_NAME="projects_zd43"
ENV DB_USER="root"
ENV DB_PASSWORD="lqeyxgvLCQkkoX3vtXoQbOcn04577vZn"
# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]