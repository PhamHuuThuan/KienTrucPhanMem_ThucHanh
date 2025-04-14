FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/discovery-service-1.0-SNAPSHOT.jar /app/discovery-service.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app/discovery-service.jar"] 