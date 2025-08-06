package com.vimasig.reservationservice;

import com.vimasig.reservationservice.model.Reservation;
import com.vimasig.reservationservice.repository.ReservationRepository;
import com.vimasig.reservationservice.service.KafkaProducerService;
import com.vimasig.reservationservice.service.ReservationService;
import com.vimasig.reservationservice.mapper.ReservationEventMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private KafkaProducerService kafkaProducerService;
    @Mock
    private ReservationEventMapper reservationEventMapper;
    @InjectMocks
    private ReservationService reservationService;

    public ReservationServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReservationSuccess() {
        Reservation reservation = new Reservation();
        reservation.setHotelId(1L);
        reservation.setRoomId(1L);
        reservation.setCheckInDate(LocalDate.of(2025, 8, 10));
        reservation.setCheckOutDate(LocalDate.of(2025, 8, 12));
        when(reservationRepository.findOverlappingReservations(anyLong(), anyLong(), any(), any())).thenReturn(Collections.emptyList());
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        Reservation result = reservationService.createReservation(reservation);
        assertNotNull(result);
        assertEquals(1L, result.getHotelId());
        assertEquals(1L, result.getRoomId());
        assertEquals(LocalDate.of(2025, 8, 10), result.getCheckInDate());
        assertEquals(LocalDate.of(2025, 8, 12), result.getCheckOutDate());
    }

    @Test
    void testCreateReservationConflict() {
        Reservation reservation = new Reservation();
        reservation.setHotelId(1L);
        reservation.setRoomId(1L);
        reservation.setCheckInDate(LocalDate.of(2025, 8, 10));
        reservation.setCheckOutDate(LocalDate.of(2025, 8, 12));
        when(reservationRepository.findOverlappingReservations(anyLong(), anyLong(), any(), any())).thenReturn(Collections.singletonList(new Reservation()));
        assertThrows(IllegalStateException.class, () -> reservationService.createReservation(reservation));
    }
}

