FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/inventory-service-0.0.1-SNAPSHOT.jar inventory-service.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "inventory-service.jar"] 