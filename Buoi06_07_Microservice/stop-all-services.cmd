@echo off
echo Stopping all services...
docker-compose down

echo Removed containers:
docker ps -a

pause 