#!/bin/bash
echo "Starting all services with Docker Compose..."

echo "Stop and remove any existing containers..."
docker-compose down

echo "Rebuilding all images..."
docker-compose build --no-cache

echo "Starting services..."
docker-compose up -d

echo "Services status:"
docker-compose ps

echo "Logs can be viewed with: docker-compose logs -f [service-name]"
echo "Access Eureka at: http://localhost:8761"
echo "Access API Gateway at: http://localhost:8080" 