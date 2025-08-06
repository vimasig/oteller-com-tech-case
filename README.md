# Oteller.com Tech Case

This repository contains a microservices-based hotel reservation system. The project is split into several services:

- **api-gateway**: Handles routing and authentication.
- **hotel-service**: Manages hotel data.
- **reservation-service**: Handles reservations.
- **notification-service**: Sends notifications.

## How to Run

1. **Build the project (skip tests):**
   > Tests require database connectivity. If you want to run tests, make sure the databases are up (see below).
   ```sh
   mvn -DskipTests clean install
   ```

2. **Start all services with Docker Compose:**
   ```sh
   docker compose -f docker-compose.yml up
   ```

   This will start all required services and databases.

### Running Tests
- By default, tests are skipped because they require database connectivity.
- To run tests, ensure the databases are running (either via `compose.yml` or manually without Docker).

## API Testing with Postman
- The repository includes a Postman collection file (`Oteller.com.postman_collection.json`).
- Import this collection into Postman to test the API endpoints.

## Additional Notes
- Configuration files for environments and API endpoints are provided in the repository.
- See `API_ENDPOINTS.md` for a list of available API routes and authentication configuration details.

---

For more details, refer to the individual service folders and documentation files.
