# Stage 1: Build stage
FROM node:18 AS builder

WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install --production  # Chỉ cài production dependencies
COPY . .

# Stage 2: Run stage
FROM node:18-alpine

WORKDIR /app
# Copy package.json trước
COPY --from=builder /app/package*.json ./
# Cài đặt lại production dependencies (thay vì copy node_modules)
RUN npm ci --only=production
# Copy file ứng dụng
COPY --from=builder /app/app.js ./

EXPOSE 3000
CMD ["node", "app.js"]