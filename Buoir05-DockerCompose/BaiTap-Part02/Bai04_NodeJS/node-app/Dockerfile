# Build stage
FROM node:18-alpine AS builder
WORKDIR /app

# Copy package files từ thư mục src
COPY --chown=node:node src/package*.json ./
RUN npm install

# Copy toàn bộ src vào app
COPY --chown=node:node src/. .
RUN chown -R node:node /app

# Runtime stage
FROM node:18-alpine
WORKDIR /app
COPY --from=builder --chown=node:node /app .
EXPOSE 3000
USER node
CMD ["npm", "start"]