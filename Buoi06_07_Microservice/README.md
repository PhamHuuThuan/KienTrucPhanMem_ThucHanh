# Hệ thống E-Commerce với Kiến trúc Microservices

Đây là hệ thống thương mại điện tử sử dụng kiến trúc microservices với Spring Boot, Eureka Server và API Gateway.

## Kiến trúc Tổng quan

Hệ thống bao gồm 7 microservices được tích hợp với nhau:

![Microservices Architecture](https://miro.medium.com/v2/resize:fit:1400/format:webp/1*7VOyd6UU8iSymJTVVmDdwA.png)

### Core Services
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

## Tương tác giữa các Service

Các service tương tác với nhau để hoàn thiện business flow:

1. **Flow đặt hàng**:
   - Customer tạo đơn hàng → Order Service
   - Order Service kiểm tra tồn kho → Inventory Service
   - Order Service khởi tạo thanh toán → Payment Service
   - Khi thanh toán hoàn tất → Shipping Service chuẩn bị giao hàng

2. **Flow thanh toán**:
   - Payment Service xử lý giao dịch
   - Cập nhật trạng thái đơn hàng → Order Service
   - Cập nhật tồn kho → Inventory Service

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

1. Khởi động tất cả services:
   ```
   docker-compose down
   docker-compose up -d
   ```
   hoặc sử dụng script có sẵn:
   ```
   ./run-all-services.cmd
   ```

2. Kiểm tra status của các services trong Eureka:
   http://localhost:8761

   Tất cả 7 services sau phải được hiển thị trong Eureka:
   - PRODUCT-SERVICE
   - CUSTOMER-SERVICE
   - ORDER-SERVICE
   - PAYMENT-SERVICE
   - INVENTORY-SERVICE
   - SHIPPING-SERVICE
   - API-GATEWAY

3. Truy cập services thông qua API Gateway:
   http://localhost:8080/api/{service-endpoint}

4. Kiểm tra logs của service:
   ```
   docker-compose logs -f [service-name]
   ```
   Ví dụ: `docker-compose logs -f payment-service`

5. Dừng tất cả services:
   ```
   docker-compose down
   ```

## Cấu trúc Dữ liệu

Mỗi service sử dụng database H2 in-memory riêng biệt:

- Product Service: H2 DB - productdb
- Customer Service: H2 DB - customerdb
- Order Service: H2 DB - orderdb
- Payment Service: H2 DB - paymentdb
- Inventory Service: H2 DB - inventorydb
- Shipping Service: H2 DB - shippingdb

H2 Console có thể truy cập tại: http://localhost:8080/{service-path}/h2-console
Ví dụ: http://localhost:8080/api/payments/h2-console

## Testing với Postman

Sử dụng Postman Collection đính kèm để test tất cả các endpoints. Collection bao gồm các request cho tất cả 7 services.

## Troubleshooting

1. **Service không xuất hiện trong Eureka**:
   - Kiểm tra logs: `docker-compose logs -f [service-name]`
   - Đảm bảo không có conflict port giữa các container
   - Kiểm tra cấu hình EUREKA_CLIENT_SERVICEURL_DEFAULTZONE

2. **Không thể truy cập API thông qua Gateway**:
   - Kiểm tra nếu gateway được đăng ký trong Eureka
   - Kiểm tra cấu hình routes trong API Gateway
   - Đảm bảo service endpoint đúng

3. **Lỗi kết nối giữa các services**:
   - Đảm bảo tất cả services đều trong cùng network
   - Kiểm tra URL service sử dụng để gọi service khác