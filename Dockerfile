# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copy Maven wrapper
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Make mvnw executable
RUN chmod +x mvnw

# Download dependencies (cache layer)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build application
RUN ./mvnw clean package -DskipTests -B

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install utilities for healthcheck
RUN apk add --no-cache wget curl

# Copy jar from builder
COPY --from=builder /app/target/*.jar app.jar

# Create non-root user
RUN addgroup -S spring && \
    adduser -S spring -G spring && \
    chown -R spring:spring /app

USER spring:spring

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# JVM options
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Run application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]