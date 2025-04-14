@echo off
echo Starting all services with Docker Compose...
docker-compose down
docker-compose up -d

echo Services status:
docker-compose ps

echo Logs can be viewed with: docker-compose logs -f [service-name]
echo Access Eureka at: http://localhost:8761
echo Access API Gateway at: http://localhost:8080

pause 