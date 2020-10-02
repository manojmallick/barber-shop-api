./mvnw.cmd clean install

docker-compose build
docker-compose up -d

echo "Barber api  is running on 8088 port & you can check health on: http://localhost:8088/actuator/health"
