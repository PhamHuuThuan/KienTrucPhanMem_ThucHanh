# Microservices E-Commerce System

This project is a simple e-commerce system implemented using Spring Boot microservices architecture.

## Architecture

The system consists of the following microservices:

- **Product Service** (port 8081): Manages product information (name, price, description, inventory)
- **Customer Service** (port 8083): Manages customer information (name, email, address, phone)
- **Order Service** (port 8082): Manages orders and communicates with product and customer services
- **API Gateway** (port 8080): Single entry point for clients to interact with all services

## Technology Stack

- Java 11
- Spring Boot 2.7.3
- Spring Data JPA
- H2 Database (in-memory)
- Spring Cloud Gateway
- Spring WebFlux for inter-service communication

## API Endpoints

### Product Service
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### Customer Service
- `GET /api/customers` - Get all customers
- `GET /api/customers/{id}` - Get customer by ID
- `POST /api/customers` - Create new customer
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer

### Order Service
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/orders/customer/{customerId}` - Get orders by customer ID
- `POST /api/orders` - Create new order
- `DELETE /api/orders/{id}` - Delete order

## Running the Application

1. Build all services:
   ```
   mvn clean package
   ```

2. Start each service in separate terminals:
   ```
   java -jar product-service/target/product-service-1.0-SNAPSHOT.jar
   java -jar customer-service/target/customer-service-1.0-SNAPSHOT.jar
   java -jar order-service/target/order-service-1.0-SNAPSHOT.jar
   java -jar api-gateway/target/api-gateway-1.0-SNAPSHOT.jar
   ```

3. Access the API through the gateway at: http://localhost:8080

## Database

Each service has its own H2 in-memory database:

- Product Service: http://localhost:8081/h2-console
- Customer Service: http://localhost:8083/h2-console
- Order Service: http://localhost:8082/h2-console

JDBC URL, username and password are set in each service's application.properties file. 