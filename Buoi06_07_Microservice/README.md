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

## Docker Support

### Chạy bằng Docker Compose

1. Build tất cả các dịch vụ:
```
mvn clean package -DskipTests
```

2. Chạy tất cả các dịch vụ bằng Docker Compose:
```
docker-compose up -d
```

3. Kiểm tra trạng thái các container:
```
docker-compose ps
```

4. Truy cập Eureka Server:
```
http://localhost:8761
```

5. Truy cập API Gateway:
```
http://localhost:8080
```

### Chạy từng dịch vụ riêng lẻ với Docker

1. Build Docker image cho từng dịch vụ:
```
docker build -t payment-service ./payment-service
docker build -t inventory-service ./inventory-service
docker build -t shipping-service ./shipping-service
```

2. Chạy các container:
```
docker run -d -p 8081:8080 --name payment-service payment-service
docker run -d -p 8082:8080 --name inventory-service inventory-service
docker run -d -p 8083:8080 --name shipping-service shipping-service
```

### Truy cập cơ sở dữ liệu H2 Console từ Docker

Mỗi dịch vụ đều có H2 console riêng, truy cập qua:

- Payment Service: http://localhost:8081/h2-console
- Inventory Service: http://localhost:8082/h2-console
- Shipping Service: http://localhost:8083/h2-console

Thông tin kết nối:
- JDBC URL: jdbc:h2:mem:paymentdb (hoặc inventorydb, shippingdb tương ứng)
- Username: sa
- Password: password

### Dừng và xóa container

```
docker-compose down
``` 