FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy frontend build
COPY frontend/dist/ /app/static/

# Copy backend JAR
COPY backend/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]