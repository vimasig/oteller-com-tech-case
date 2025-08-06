# REST API Endpoints Documentation

This document describes the available REST API endpoints for the Oteller.com Tech Case microservices.

---

## hotel-service

### HotelController
- `GET /api/hotels` - Retrieve all hotels.
- `POST /api/hotels` - Create a new hotel.
- `GET /api/hotels/{id}` - Retrieve a hotel by its ID.
- `PUT /api/hotels/{id}` - Update an existing hotel by its ID.
- `DELETE /api/hotels/{id}` - Delete a hotel by its ID.

### RoomController
- `GET /api/rooms` - Retrieve all rooms.
- `POST /api/rooms` - Create a new room.
- `GET /api/rooms/hotel/{hotelId}` - Retrieve all rooms for a specific hotel.
- `GET /api/rooms/{id}` - Retrieve a room by its ID.
- `PUT /api/rooms/{id}` - Update an existing room by its ID.
- `DELETE /api/rooms/{id}` - Delete a room by its ID.

---

## reservation-service

### ReservationController
- `GET /api/reservations` - Retrieve all reservations.
- `POST /api/reservations` - Create a new reservation.
- `GET /api/reservations/{id}` - Retrieve a reservation by its ID.
- `PUT /api/reservations/{id}` - Update a reservation by its ID.
- `DELETE /api/reservations/{id}` - Delete a reservation by its ID.

---

## Authentication
- All endpoints require a valid JWT token in the `Authorization` header (format: `Bearer <token>`).
- JWT secret: `a-string-secret-at-least-256-bits-long`
- JWT algorithm: `HS256`
- Authentication is always required; only some endpoints perform additional authorization.

---

## Notes
- The api-gateway and notification-service do not expose direct REST endpoints.

For more details on request/response formats, refer to the respective service source code.
