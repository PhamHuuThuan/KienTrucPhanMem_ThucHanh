FROM maven:3.8-openjdk-11 as build

WORKDIR /app

# Copy pom.xml files
COPY pom.xml .
COPY api-gateway/pom.xml api-gateway/
COPY customer-service/pom.xml customer-service/
COPY order-service/pom.xml order-service/
COPY product-service/pom.xml product-service/
COPY payment-service/pom.xml payment-service/
COPY inventory-service/pom.xml inventory-service/
COPY shipping-service/pom.xml shipping-service/

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY api-gateway/ api-gateway/
COPY customer-service/ customer-service/
COPY order-service/ order-service/
COPY product-service/ product-service/
COPY payment-service/ payment-service/
COPY inventory-service/ inventory-service/
COPY shipping-service/ shipping-service/

# Build all services
RUN mvn clean package -DskipTests

# Multistage build for smaller final image
FROM openjdk:11-jdk-slim

WORKDIR /app

# Copy built jars from the build stage
COPY --from=build /app/api-gateway/target/api-gateway-*.jar api-gateway.jar
COPY --from=build /app/customer-service/target/customer-service-*.jar customer-service.jar
COPY --from=build /app/order-service/target/order-service-*.jar order-service.jar
COPY --from=build /app/product-service/target/product-service-*.jar product-service.jar
COPY --from=build /app/payment-service/target/payment-service-*.jar payment-service.jar
COPY --from=build /app/inventory-service/target/inventory-service-*.jar inventory-service.jar
COPY --from=build /app/shipping-service/target/shipping-service-*.jar shipping-service.jar

# Copy docker-compose.yml
COPY docker-compose.yml .

# Install Docker CLI for docker-compose
RUN apt-get update && \
    apt-get install -y apt-transport-https ca-certificates curl gnupg lsb-release && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg && \
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list > /dev/null && \
    apt-get update && \
    apt-get install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin && \
    apt-get clean

# Entry point
ENTRYPOINT ["docker-compose", "up"] 