# Barber Shop API

REST backend for a barber-shop booking system. Customers can see which barbers are free, book a 30-minute appointment with one, get a confirmation email, and receive a reminder an hour before the appointment.

The matching Angular UI lives in [barber-shop-client](https://github.com/manojmallick/barber-shop-client).

> **The brief:** a shop owner wants a booking system where customers book with the barber of their choice. He can't collect customer feedback, send reminders or gather any metrics to grow his business. The work was split into phases, below.

## Features

### Delivered

| Phase | Capability |
|---|---|
| 1 | Book a barber. If you don't pick one, or your pick is busy, an available barber is assigned automatically. Every booking claims a 30-minute slot. |
| 2 | See availability for a date (or date range) per shop, with each barber's schedule. Barbers carry an `experience` level. |
| 3 | Email reminder about one hour before the appointment (scheduled job). |
| Extra | Multi-shop: every barber, service and appointment belongs to a shop (`shopId`). |
| Extra | Confirmation email after booking (Velocity/Thymeleaf templates, SMTP). |
| Extra | Services (for example haircut types) are served from the API. |
| Extra | Validated requests with a consistent JSON error format. |
| Extra | Swagger UI, Actuator health endpoint, Docker setup for dev and prod. |

### Not yet built

- Customer self-service to change or cancel an appointment
- Recurring bookings, waiting list
- Shop-owner metrics
- UI for managing services (the API side exists)

## Tech stack

Java 8, Spring Boot 2.2.5 (Web, Data MongoDB, Actuator, Mail, Thymeleaf), MongoDB 4.2, Springfox Swagger 2, Lombok, log4j2, JUnit and Mockito, Maven (wrapper included), Docker and Docker Compose.

## Quick start (Docker)

You need Docker with Compose, and a JDK (the scripts run `./mvnw` to build the jar before Docker packages it).

```bash
git clone https://github.com/manojmallick/barber-shop-api.git
cd barber-shop-api

# 1. Create your .env from the template and set the database password and port
#    (see "Configuration" below). .env is git-ignored.
cp .env.example .env
$EDITOR .env

# 2a. Development (API on http://localhost:8080)
sh ./run-dev.sh

# 2b. Production-style (API on http://localhost:8088)
sh ./run.sh
```

On Windows use `run-dev.bat` or `run.bat`. If the shell scripts are not executable, run `chmod +x run.sh run-dev.sh` first.

Both scripts build the jar (`./mvnw clean install`) and start the API together with a MongoDB container. MongoDB is seeded with sample barbers, services and a shop from `docker/barber/init-scripts/mongodb-init.js`.

Check that it is up:

```bash
curl http://localhost:8080/api/actuator/health     # dev
curl http://localhost:8088/api/actuator/health     # prod
```

> The API is served under the `/api` context path.

## Run without Docker

Requirements: JDK 8+, Maven (or the included `./mvnw`), a running MongoDB, and Lombok support in your IDE (IntelliJ or Eclipse plugin).

```bash
export MONGO_DB_HOST=localhost MONGO_DB_PORT=27017
export MONGO_DB_USERNAME=<user> MONGO_DB_PASSWORD=<password> MONGO_DB_DATABASE=barber
./mvnw spring-boot:run
```

The app listens on `APP_PORT` (default `8080`).

## Configuration

Docker Compose reads these from `.env` in the project root (create it with `cp .env.example .env`; it is not committed); the app reads the `MONGO_DB_*`, `LOG_LEVEL` and `APP_PORT` equivalents (see `src/main/resources/application.properties`).

| `.env` variable | App setting | Meaning | Default in app |
|---|---|---|---|
| `DB_HOST` | `MONGO_DB_HOST` | MongoDB host | `localhost` |
| `DB_PORT` | `MONGO_DB_PORT` | MongoDB port | `27017` |
| `DB_USERNAME` | `MONGO_DB_USERNAME` | MongoDB user | `root` |
| `DB_PASSWORD` | `MONGO_DB_PASSWORD` | MongoDB password | none, set it |
| `DB_DATABASE` | `MONGO_DB_DATABASE` | Database name | `barber` |
| `LOG_LEVEL` | `LOG_LEVEL` | Log level | `DEBUG` |
| `APP_PORT` | `APP_PORT` | HTTP port | `8080` |

Email (booking confirmation and reminders) is configured with the `mail.*` properties in `application.properties` (SMTP host, port, username, password, sender address). Set these to your own SMTP provider before relying on email.

## API

Base path: `/api`. Interactive docs: **`/api/swagger-ui.html`**.

| Method | Path | Description |
|---|---|---|
| `GET` | `/v1/shops` | List shops |
| `GET` | `/v1/barbers` | List barbers, most experienced first |
| `GET` | `/v1/services` | List services (JSON or XML) |
| `GET` | `/v1/availabilities` | Availability for a date. Query: `bookingDate` (`yyyy-MM-dd`, today or later, required), `endDate` (optional, same format), `shopId` (required, must exist) |
| `POST` | `/v1/appointments` | Create an appointment |
| `GET` | `/v1/appointments` | List appointments |
| `GET` | `/v1/appointments/{id}` | Get one appointment |

### Example: check availability

```bash
curl "http://localhost:8080/api/v1/availabilities?bookingDate=2030-01-15&shopId=<SHOP_ID>"
```

### Example: book an appointment

`customer.name`, `customer.gender`, `customer.mobile`, `shop`, `startTime` and `bookingDate` are required; `barber` and `services` are optional (a free barber is assigned when `barber` is omitted or already booked), and `customer.email` is optional but needed for the confirmation and reminder emails. Get valid shop, barber and service IDs from the list endpoints above. The exact request schema is in Swagger UI.

```bash
curl -X POST http://localhost:8080/api/v1/appointments \
  -H "Content-Type: application/json" \
  -d '{
        "customer": { "name": "Jane Doe", "gender": "female",
                      "mobile": "0612345678", "email": "jane@example.com" },
        "shop":     { "id": "<SHOP_ID>" },
        "barber":   { "id": "<BARBER_ID>" },
        "services": [ { "id": "<SERVICE_ID>" } ],
        "startTime": "10:00",
        "bookingDate": "2030-01-15"
      }'
```

### Errors

Validation and lookup failures return a structured JSON error (`ApiError`) with an error code and message, for example a missing query parameter, an invalid date, or a shop or appointment that does not exist.

## How reminders work

A scheduled job (`ScheduledTasks`) runs every 30 minutes during opening hours (weekdays and weekends have separate cron windows). It loads today's appointments and sends a reminder email to those starting within the next hour.

## Project structure

```
src/main/java/com/ing/barber/shop/api/
├── appointment/    Controller, service, repository, model
├── availability/   Availability calculation and endpoint
├── barber/         Barbers and their schedules
├── shop/           Shops
├── services/       Services offered
├── email/          SMTP config, templates, email service
├── scheduler/      Reminder job
├── config/         Mongo, CORS, Swagger, Velocity, properties
├── error/          Exceptions, error codes, global handler
└── beans/          Customer, Day, Schedule
docker/barber/init-scripts/mongodb-init.js   Sample data
```

## Tests

```bash
./mvnw test
```

Unit tests cover the appointment, availability, barber, services and shop services, the error handler and utilities (JUnit and Mockito).

## Roadmap

See "Not yet built" above. Other ideas: authentication for shop owners, a CI pipeline and build badge, and a hosted demo.

## License

MIT © [Manoj Mallick](https://github.com/manojmallick)
