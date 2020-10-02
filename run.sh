set -e

./mvnw clean install
docker-compose up -d --build
echo "Barber api  is running on 8088 port & you can check health on: http://localhost:8088/actuator/health"
