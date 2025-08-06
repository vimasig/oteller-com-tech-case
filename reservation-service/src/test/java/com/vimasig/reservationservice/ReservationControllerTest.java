package com.vimasig.reservationservice;

import com.vimasig.reservationservice.controller.ReservationController;
import com.vimasig.reservationservice.dto.ReservationDTO;
import com.vimasig.reservationservice.mapper.ReservationMapper;
import com.vimasig.reservationservice.model.Reservation;
import com.vimasig.reservationservice.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationControllerTest {
    @Mock
    private ReservationService reservationService;
    @Mock
    private ReservationMapper reservationMapper;
    @InjectMocks
    private ReservationController reservationController;

    public ReservationControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReservationById_UserIsGuest() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setGuestName("John Doe");
        when(reservationService.getReservationById(1L)).thenReturn(Optional.of(reservation));
        ReservationDTO dto = new ReservationDTO();
        dto.setGuestName("John Doe");
        when(reservationMapper.toDTO(reservation)).thenReturn(dto);
        ResponseEntity<ReservationDTO> response = reservationController.getReservationById("John Doe", 1L);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getGuestName());
    }

    @Test
    void testGetReservationById_UserIsNotGuest() {
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setGuestName("Jane Doe");
        when(reservationService.getReservationById(1L)).thenReturn(Optional.of(reservation));
        ResponseEntity<ReservationDTO> response = reservationController.getReservationById("John Doe", 1L);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testGetAllReservations_FilteredByUser() {
        Reservation reservation = new Reservation();
        reservation.setGuestName("John Doe");
        when(reservationService.getReservationsByGuestName("John Doe")).thenReturn(List.of(reservation));
        ReservationDTO dto = new ReservationDTO();
        dto.setGuestName("John Doe");
        when(reservationMapper.toDTO(reservation)).thenReturn(dto);
        List<ReservationDTO> result = reservationController.getAllReservations("John Doe");
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getGuestName());
    }
}

