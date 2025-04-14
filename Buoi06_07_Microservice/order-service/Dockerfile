FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/order-service-1.0-SNAPSHOT.jar order-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "order-service.jar"] 