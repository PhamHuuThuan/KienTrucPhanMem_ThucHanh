@echo off
echo Building all services with Maven...
call mvn clean package -DskipTests

echo Starting all services with Docker Compose...
docker-compose up -d

echo Services status:
docker-compose ps

echo Logs can be viewed with: docker-compose logs -f [service-name]
echo Access Eureka at: http://localhost:8761
echo Access API Gateway at: http://localhost:8080

pause 