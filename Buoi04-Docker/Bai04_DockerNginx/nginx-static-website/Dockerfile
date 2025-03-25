# Sử dụng image Nginx chính thức
FROM nginx:latest

# Xóa file mặc định trong thư mục html của Nginx
RUN rm -rf /usr/share/nginx/html/*

# Copy file index.html vào thư mục html của Nginx
COPY index.html /usr/share/nginx/html/

# Expose cổng 80 để truy cập web
EXPOSE 80

# Lệnh chạy Nginx khi container khởi động
CMD ["nginx", "-g", "daemon off;"]