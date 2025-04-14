FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/shipping-service-0.0.1-SNAPSHOT.jar shipping-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "shipping-service.jar"] 