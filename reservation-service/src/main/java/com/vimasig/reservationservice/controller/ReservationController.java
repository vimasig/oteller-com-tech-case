package com.vimasig.reservationservice.controller;

import com.vimasig.reservationservice.dto.ReservationDTO;
import com.vimasig.reservationservice.mapper.ReservationMapper;
import com.vimasig.reservationservice.model.Reservation;
import com.vimasig.reservationservice.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing hotel room reservations.
 * <p>
 * Security: Only authenticated users can access their own reservations.
 * <p>
 * Per case requirements, only GET endpoints are protected and filtered by user.
 * <p>
 * If a reservation is made for another person, only that person (whose name matches the guestName) can retrieve the reservation. The creator cannot retrieve it unless they are the guest.
 * <p>
 * Create, update, and delete endpoints are not restricted by user identity.
 */

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @GetMapping
    public List<ReservationDTO> getAllReservations(@RequestHeader("X-User-Name") String userName) {
        return reservationService.getReservationsByGuestName(userName).stream()
                .map(reservationMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@RequestHeader("X-User-Name") String userName, @PathVariable("id") Long id) {
        return reservationService.getReservationById(id)
                .filter(reservation -> reservation.getGuestName().equals(userName))
                .map(reservationMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ReservationDTO createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        if (true)
            throw new RuntimeException("Test exception for reservation creation");
        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        Reservation saved = reservationService.createReservation(reservation);
        return reservationMapper.toDTO(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable("id") Long id, @Valid @RequestBody ReservationDTO reservationDTO) {
        Reservation updated = reservationService.updateReservation(id, reservationMapper.toEntity(reservationDTO));
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
