# Use official OpenJDK 17 image
FROM openjdk:17-jdk-slim

# # Set working directory
# WORKDIR /app

# Copy JAR file into container (same value as finalName in pom)
COPY target/ecommerce.jar ecommerce.jar

# Run the application
ENTRYPOINT ["java", "-jar", "ecommerce.jar"]
