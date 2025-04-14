FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/product-service-1.0-SNAPSHOT.jar product-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "product-service.jar"] 