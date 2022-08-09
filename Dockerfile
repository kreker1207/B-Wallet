FROM openjdk:17-slim

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ARG SERVICE_PORT=8080
EXPOSE ${SERVICE_PORT}

ENV JAVA_TOOL_OPTIONS=""

ENTRYPOINT [ "java", "-jar", "/app.jar" ]
