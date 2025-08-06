package com.vimasig.reservationservice.service;

import com.vimasig.reservationservice.dto.ReservationEventDTO;
import com.vimasig.reservationservice.model.Reservation;
import com.vimasig.reservationservice.repository.ReservationRepository;
import com.vimasig.reservationservice.mapper.ReservationEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final KafkaProducerService kafkaProducerService;
    private final ReservationEventMapper reservationEventMapper;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getReservationsByGuestName(String guestName) {
        return reservationRepository.findByGuestName(guestName);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Reservation createReservation(Reservation reservation) {
        boolean hasCollision = !reservationRepository.findOverlappingReservations(
                reservation.getHotelId(),
                reservation.getRoomId(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate()
        ).isEmpty();
        if (hasCollision) {
            throw new IllegalStateException("Reservation collides with an existing reservation.");
        }
        Reservation saved = reservationRepository.save(reservation);
        ReservationEventDTO eventDTO = reservationEventMapper.toEventDTO(saved);
        kafkaProducerService.sendCreateEvent(eventDTO);
        return saved;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        return reservationRepository.findById(id).map(reservation -> {
            boolean hasCollision = reservationRepository.findOverlappingReservations(
                    reservationDetails.getHotelId(),
                    reservationDetails.getRoomId(),
                    reservationDetails.getCheckInDate(),
                    reservationDetails.getCheckOutDate()
            ).stream().anyMatch(r -> !r.getId().equals(id));
            if (hasCollision) {
                throw new IllegalStateException("Reservation collides with an existing reservation.");
            }
            reservation.setHotelId(reservationDetails.getHotelId());
            reservation.setRoomId(reservationDetails.getRoomId());
            reservation.setGuestName(reservationDetails.getGuestName());
            reservation.setCheckInDate(reservationDetails.getCheckInDate());
            reservation.setCheckOutDate(reservationDetails.getCheckOutDate());
            Reservation updated = reservationRepository.save(reservation);
            ReservationEventDTO eventDTO = reservationEventMapper.toEventDTO(updated);
            kafkaProducerService.sendUpdateEvent(eventDTO);
            return updated;
        }).orElse(null);
    }

    @Transactional
    public void deleteReservation(Long id) {
        reservationRepository.findById(id).ifPresent(reservation -> {
            ReservationEventDTO eventDTO = reservationEventMapper.toEventDTO(reservation);
            kafkaProducerService.sendDeleteEvent(eventDTO);
        });
        reservationRepository.deleteById(id);
    }
}
