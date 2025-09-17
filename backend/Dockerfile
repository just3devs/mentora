# ------------ Build stage ------------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Download dependencies
COPY pom.xml ./
RUN mvn -q -e -B -DskipTests dependency:go-offline

# Copy source code
COPY src ./src
# Layered jar
RUN mvn -q -e -B -DskipTests clean package

# ------------ Run stage ------------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy jar
COPY --from=build /app/target/*.jar app.jar

# Healthcheck
HEALTHCHECK --interval=30s --timeout=3s --retries=5 CMD \
  wget -qO- http://localhost:8080/actuator/health | grep -q '"status":"UP"' || exit 1

EXPOSE 8080

# set JVM flags and run the jar
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
