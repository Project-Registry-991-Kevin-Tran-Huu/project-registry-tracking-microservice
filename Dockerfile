FROM maven:3.6.3-jdk-11-slim as builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests=true
RUN cp target/app.jar app.jar

FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
WORKDIR /app
ARG DEPENDENCY=/app
COPY --from=builder $DEPENDENCY/dependencies/ ./
COPY --from=builder $DEPENDENCY/snapshot-dependencies/ ./
COPY --from=builder $DEPENDENCY/spring-boot-loader/ ./
COPY --from=builder $DEPENDENCY/application/ ./
ENTRYPOINT ["java","-jar","/app.jar"]