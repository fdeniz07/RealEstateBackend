# RealEstateBackend — multi-stage image (Java 17, Spring Boot 4.1.x)
# Secrets are supplied at runtime via environment variables only.

FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Repository ships mvnw scripts but .mvn/wrapper is not checked in; use image Maven (3.9.9, Java 17).
COPY pom.xml ./
COPY src src

RUN mvn -B -DskipTests package

FROM eclipse-temurin:17-jre-jammy AS runtime

RUN apt-get update \
    && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/* \
    && groupadd --system spring \
    && useradd --system --gid spring spring

WORKDIR /app

COPY --from=build --chown=spring:spring /app/target/*.jar /app/app.jar

USER spring:spring

EXPOSE 8080

ENV JAVA_TOOL_OPTIONS="-XX:+ExitOnOutOfMemoryError"

ENTRYPOINT ["java", "-jar", "/app/app.jar"]