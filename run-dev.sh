set -e
./mvnw clean install
docker-compose -f docker-compose-dev.yml up -d --build
echo "Barber api  is running on 8080 port & you can check health on: http://localhost:8080/actuator/health"
