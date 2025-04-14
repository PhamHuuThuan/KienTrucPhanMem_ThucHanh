FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/customer-service-1.0-SNAPSHOT.jar customer-service.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "customer-service.jar"] 