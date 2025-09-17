# ------------ Build stage ------------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml ./
RUN mvn -e -B -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -e -B -DskipTests clean package

# ------------ Run stage ------------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN apk add --no-cache curl

COPY --from=build /app/target/*.jar app.jar

HEALTHCHECK --interval=30s --timeout=3s --retries=5 CMD \
  curl -fsS http://localhost:8080/actuator/health | grep -q '"status":"UP"' || exit 1

EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
