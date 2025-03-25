# Giai đoạn 1: Build ứng dụng Go
FROM golang:1.21-alpine AS builder

# Tạo thư mục làm việc
WORKDIR /app

# Copy mã nguồn vào container
COPY . .

# Tải các dependencies
RUN go mod init hello-go
RUN go mod tidy

# Build ứng dụng
RUN CGO_ENABLED=0 GOOS=linux go build -o hello-go

# Giai đoạn 2: Tạo image chạy ứng dụng
FROM alpine:latest

WORKDIR /root/

# Copy binary từ builder
COPY --from=builder /app/hello-go .

# Expose cổng ứng dụng sẽ chạy
EXPOSE 8080

# Lệnh chạy ứng dụng
CMD ["./hello-go"]