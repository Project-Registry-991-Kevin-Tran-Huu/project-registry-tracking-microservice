FROM maven:3.6.3-jdk-11-slim as builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests=true

FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]