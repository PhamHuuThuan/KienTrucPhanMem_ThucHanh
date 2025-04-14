FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/shipping-service-0.0.1-SNAPSHOT.jar shipping-service.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "shipping-service.jar"] 