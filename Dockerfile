FROM eclipse-temurin:17-jre

WORKDIR /app

ARG APP_VERSION=unknown
ARG SOURCE_REPOSITORY=unknown

LABEL org.opencontainers.image.title="github-actions-java-demo"
LABEL org.opencontainers.image.description="Spring Boot GitHub Actions CI/CD demo"
LABEL org.opencontainers.image.source="${SOURCE_REPOSITORY}"
LABEL org.opencontainers.image.version="${APP_VERSION}"

COPY docker-input/github-actions-demo.jar /app/app.jar

ENV APP_VERSION=${APP_VERSION}
ENV APP_ENVIRONMENT=DEV

EXPOSE 8080

HEALTHCHECK --interval=10s \
            --timeout=3s \
            --start-period=20s \
            --retries=5 \
    CMD ["sh", "-c", "wget -qO- http://localhost:8080/actuator/health | grep -q '\"status\":\"UP\"'"]

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
