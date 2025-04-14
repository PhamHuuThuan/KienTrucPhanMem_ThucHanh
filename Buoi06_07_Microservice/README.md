# Hệ thống E-Commerce với Kiến trúc Microservices

Đây là hệ thống thương mại điện tử sử dụng kiến trúc microservices với Spring Boot, Eureka Server và API Gateway.

## Kiến trúc Tổng quan

Hệ thống bao gồm 8 microservices được tích hợp với nhau:

### Infrastructure Services
1. **Discovery Service** (Eureka - port 8761)
   - Đăng ký và phát hiện các services
   - Cung cấp giao diện quản lý dịch vụ

2. **API Gateway** (port 8080)
   - Điểm truy cập tập trung cho tất cả requests
   - Định tuyến requests đến các services thích hợp
   - Hỗ trợ load balancing và fault tolerance

### Domain Services
3. **Product Service** (port 8081)
   - Quản lý thông tin sản phẩm
   - Danh mục sản phẩm và tìm kiếm

4. **Customer Service** (port 8083)
   - Quản lý thông tin khách hàng
   - Xác thực và phân quyền

5. **Order Service** (port 8082)
   - Quản lý đơn hàng
   - Tương tác với product và customer service
   
6. **Payment Service** (port 8084)
   - Xử lý thanh toán
   - Quản lý trạng thái thanh toán (PENDING, COMPLETED, FAILED, REFUNDED)
   - Ghi nhận lịch sử giao dịch

7. **Inventory Service** (port 8085)
   - Quản lý tồn kho
   - Theo dõi số lượng sản phẩm
   - Cập nhật khi có đơn hàng mới

8. **Shipping Service** (port 8086)
   - Quản lý vận chuyển
   - Theo dõi trạng thái giao hàng
   - Tạo mã vận đơn và tracking

## Business Flow Tích Hợp

Mô hình tích hợp giữa 6 domain services:

1. **Quy trình đặt hàng**:
   - Khách hàng đăng nhập → Customer Service xác thực
   - Khách hàng chọn sản phẩm → Product Service cung cấp thông tin
   - Khách hàng tạo đơn hàng → Order Service lưu đơn hàng
   - Order Service kiểm tra tồn kho → Inventory Service
   - Order Service yêu cầu thanh toán → Payment Service xử lý
   - Sau khi thanh toán → Shipping Service tạo đơn vận chuyển

2. **Quy trình quản lý tồn kho**:
   - Đơn hàng được xác nhận → Inventory Service giảm số lượng tồn kho
   - Đơn hàng hủy → Inventory Service hoàn tồn kho
   - Nhập kho → Inventory Service cập nhật số lượng mới

3. **Quy trình vận chuyển và theo dõi**:
   - Đơn hàng đã thanh toán → Shipping Service tạo vận đơn
   - Cập nhật trạng thái → Shipping Service ghi nhận
   - Giao hàng thành công → Order Service cập nhật trạng thái đơn

## Công nghệ sử dụng

- **Java 11**
- **Spring Boot 2.7.3**
- **Spring Cloud**
  - Netflix Eureka (Service Discovery)
  - Spring Cloud Gateway
  - OpenFeign (Service Communication)
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Docker & Docker Compose**
- **Lombok**

## API Endpoints

Tất cả API đều được định tuyến qua API Gateway (http://localhost:8080)

### Product Service
- `GET /api/products` - Lấy tất cả sản phẩm
- `GET /api/products/{id}` - Lấy sản phẩm theo ID
- `POST /api/products` - Tạo sản phẩm mới
- `PUT /api/products/{id}` - Cập nhật sản phẩm
- `DELETE /api/products/{id}` - Xóa sản phẩm

### Customer Service
- `GET /api/customers` - Lấy tất cả khách hàng
- `GET /api/customers/{id}` - Lấy khách hàng theo ID
- `POST /api/customers` - Tạo khách hàng mới
- `PUT /api/customers/{id}` - Cập nhật khách hàng
- `DELETE /api/customers/{id}` - Xóa khách hàng

### Order Service
- `GET /api/orders` - Lấy tất cả đơn hàng
- `GET /api/orders/{id}` - Lấy đơn hàng theo ID
- `GET /api/orders/customer/{customerId}` - Lấy đơn hàng theo customer ID
- `POST /api/orders` - Tạo đơn hàng mới
- `DELETE /api/orders/{id}` - Xóa đơn hàng

### Payment Service
- `GET /api/payments` - Lấy tất cả thanh toán
- `GET /api/payments/{id}` - Lấy thanh toán theo ID
- `GET /api/payments/order/{orderId}` - Lấy thanh toán theo order ID
- `GET /api/payments/status/{status}` - Lấy thanh toán theo trạng thái
- `POST /api/payments` - Xử lý thanh toán mới
- `PUT /api/payments/{id}/status` - Cập nhật trạng thái thanh toán
- `DELETE /api/payments/{id}` - Xóa thanh toán

### Inventory Service
- `GET /api/inventory` - Lấy tất cả tồn kho
- `GET /api/inventory/{id}` - Lấy tồn kho theo ID
- `GET /api/inventory/product/{productId}` - Lấy tồn kho theo product ID
- `GET /api/inventory/status/{status}` - Lấy tồn kho theo trạng thái
- `GET /api/inventory/low-stock` - Lấy danh sách tồn kho thấp
- `POST /api/inventory` - Thêm tồn kho mới
- `PUT /api/inventory/product/{productId}/quantity` - Cập nhật số lượng tồn kho
- `PUT /api/inventory/{id}/status` - Cập nhật trạng thái tồn kho
- `DELETE /api/inventory/{id}` - Xóa tồn kho

### Shipping Service
- `GET /api/shipments` - Lấy tất cả lô hàng
- `GET /api/shipments/{id}` - Lấy lô hàng theo ID
- `GET /api/shipments/order/{orderId}` - Lấy lô hàng theo order ID
- `GET /api/shipments/tracking/{trackingNumber}` - Lấy lô hàng theo số theo dõi
- `GET /api/shipments/status/{status}` - Lấy lô hàng theo trạng thái
- `POST /api/shipments` - Tạo lô hàng mới
- `PUT /api/shipments/{id}/status` - Cập nhật trạng thái lô hàng
- `DELETE /api/shipments/{id}` - Xóa lô hàng

## Chạy ứng dụng

### Sử dụng Docker Compose

1. Đảm bảo bạn đã cài đặt Docker và Docker Compose trên máy.

2. Clone repository:
   ```
   git clone <repository-url>
   cd <repository-folder>
   ```

3. Build các service (optional, Docker Compose sẽ tự build nếu cần):
   ```
   mvn clean package -DskipTests
   ```

4. Khởi động tất cả services:
   ```
   docker-compose down
   docker-compose up -d
   ```
   hoặc sử dụng script có sẵn (nếu có):
   ```
   # Windows
   ./run-all-services.cmd
   
   # Linux/Mac (đảm bảo file có quyền thực thi)
   chmod +x ./run-all-services.sh
   ./run-all-services.sh
   ```

5. Kiểm tra status của các services trong Eureka:
   http://localhost:8761

   Các services sau phải được hiển thị trong Eureka:
   - API-GATEWAY
   - PRODUCT-SERVICE
   - CUSTOMER-SERVICE
   - ORDER-SERVICE
   - PAYMENT-SERVICE
   - INVENTORY-SERVICE
   - SHIPPING-SERVICE

6. Truy cập services thông qua API Gateway:
   http://localhost:8080/api/{service-endpoint}

7. Kiểm tra logs của service:
   ```
   docker-compose logs -f [service-name]
   ```
   Ví dụ: `docker-compose logs -f payment-service`

8. Dừng tất cả services:
   ```
   docker-compose down
   ```

9. Nếu bạn muốn xóa volumes (dữ liệu MySQL) khi dừng:
   ```
   docker-compose down -v
   ```

### Chạy thủ công (không dùng Docker)

1. Đảm bảo bạn đã cài đặt:
   - JDK 11
   - Maven
   - MySQL 8.0

2. Cấu hình các database MySQL:
   ```sql
   CREATE DATABASE productdb;
   CREATE DATABASE customerdb;
   CREATE DATABASE orderdb;
   CREATE DATABASE paymentdb;
   CREATE DATABASE inventorydb;
   CREATE DATABASE shippingdb;
   ```

3. Cập nhật application.properties của mỗi service với thông tin kết nối MySQL local:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/{tên_database}
   spring.datasource.username=root
   spring.datasource.password=password
   ```

4. Khởi động các service theo thứ tự:
   - Discovery Service (Eureka Server)
   - Các service khác
   - API Gateway (cuối cùng)

5. Mỗi service có thể khởi động bằng lệnh:
   ```
   cd <service-folder>
   mvn spring-boot:run
   ```

## Cấu trúc Dữ liệu

Mỗi service sử dụng database MySQL riêng biệt:

- Product Service: MySQL - productdb
- Customer Service: MySQL - customerdb
- Order Service: MySQL - orderdb
- Payment Service: MySQL - paymentdb
- Inventory Service: MySQL - inventorydb
- Shipping Service: MySQL - shippingdb

Các bảng dữ liệu sẽ được tự động tạo với JPA/Hibernate khi khởi động service lần đầu.

## Testing với Postman

Sử dụng Postman Collection đính kèm để test tất cả các endpoints. Collection bao gồm các request cho tất cả 6 domain services.

## Troubleshooting

1. **Service không xuất hiện trong Eureka**:
   - Kiểm tra logs: `docker-compose logs -f [service-name]`
   - Đảm bảo trong file Java Application của service có annotation `@EnableDiscoveryClient`
   - Đảm bảo trong pom.xml có dependency `spring-cloud-starter-netflix-eureka-client`
   - Kiểm tra cấu hình eureka.client.serviceUrl.defaultZone trong application.yml
   - Thử khởi động lại service: `docker-compose restart [service-name]`

2. **API Gateway không thể kết nối đến service**:
   - Kiểm tra trong API Gateway cấu hình route sử dụng tên service (không phải localhost)
   - Đảm bảo port trong cấu hình route khớp với port của service
   - Kiểm tra logs API Gateway: `docker-compose logs -f api-gateway`

3. **Không thể gọi service thông qua API Gateway**:
   - Đảm bảo service đã đăng ký thành công với Eureka
   - Kiểm tra cấu hình route trong API Gateway
   - Kiểm tra logs response từ API Gateway

4. **Lỗi phân giải tên service**:
   - Đảm bảo tất cả containers đều nằm trong cùng một network
   - Kiểm tra cấu hình dns trong network Docker

5. **Lỗi kết nối đến database**:
   - Kiểm tra logs service: `docker-compose logs -f [service-name]`
   - Đảm bảo containers MySQL đã khởi động thành công: `docker-compose ps`
   - Kiểm tra kết nối đến database từ bên trong container:
     ```
     docker-compose exec [service-name] bash
     ping [db-container-name]
     ```
   - Nếu sử dụng cách chạy thủ công, đảm bảo MySQL local đang chạy và các database đã được tạo

6. **Vấn đề về volume hoặc dữ liệu MySQL**:
   - Kiểm tra volumes đã được tạo: `docker volume ls`
   - Xóa và tạo lại volume nếu cần: `docker-compose down -v` sau đó `docker-compose up -d`
   - Đảm bảo schema đã được tạo trong các database (mặc định JPA sẽ tự tạo)