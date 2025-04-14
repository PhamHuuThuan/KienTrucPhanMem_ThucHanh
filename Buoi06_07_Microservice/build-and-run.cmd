@echo off
echo Building Discovery Service...
cd discovery-service
call mvn clean package -DskipTests
cd ..

echo Building and starting Docker container for Discovery Service...
docker-compose up -d discovery-service

echo Discovery Service started. To see logs:
echo docker logs -f discovery-service

echo Access Eureka dashboard at: http://localhost:8761

pause 