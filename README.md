# Oteller.com Tech Case

This repository contains a microservices-based hotel reservation system. The project is split into several services:

- **api-gateway**: Handles routing and authentication.
- **hotel-service**: Manages hotel data.
- **reservation-service**: Handles reservations.
- **notification-service**: Sends notifications.

## Requirements

- **Java 17** is required to build and run the project.
- **Docker & Docker Compose** are required to run all services and dependencies.

## How to Run

1. **Build the project (skip tests):**
   > Tests require Apache Kafka to be running. The project uses PostgreSQL as the default database. For testing, H2 is used, so no external database setup is needed for tests. If you want to run tests, make sure Kafka is up (see below).
   ```sh
   mvn -DskipTests clean install
   ```

2. **Start all services with Docker Compose:**
   ```sh
   docker compose -f docker-compose.yml up
   ```

   This will start all required services and dependencies, including Kafka and databases. **No manual setup for Kafka or the database is required.**

### Running Tests
- By default, tests are skipped because they require Apache Kafka connectivity.
- To run tests, ensure Kafka is running (either via `compose.yml` or manually without Docker). The tests use H2 and do not require external database setup.

## API Testing with Postman
- The repository includes a Postman collection file (`Oteller.com.postman_collection.json`).
- Import this collection into Postman to test the API endpoints.

## Additional Notes
- Configuration files for environments and API endpoints are provided in the repository.
- See `API_ENDPOINTS.md` for a list of available API routes and authentication configuration details.

---

For more details, refer to the individual service folders and documentation files.
