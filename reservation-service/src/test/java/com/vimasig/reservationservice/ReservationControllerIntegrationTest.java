package com.vimasig.reservationservice;

import com.vimasig.reservationservice.model.Reservation;
import com.vimasig.reservationservice.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ReservationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void testCreateAndGetReservation() throws Exception {
        String reservationJson = "{\"hotelId\":1,\"roomId\":1,\"guestName\":\"John Doe\",\"checkInDate\":\"2025-08-10\",\"checkOutDate\":\"2025-08-12\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guestName").value("John Doe"));

        Reservation reservation = reservationRepository.findAll().get(0);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations/" + reservation.getId())
                .header("X-User-Name", "John Doe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guestName").value("John Doe"));
    }

    @Test
    void testCreateReservationConflict() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setHotelId(1L);
        reservation.setRoomId(1L);
        reservation.setGuestName("Jane Doe");
        reservation.setCheckInDate(LocalDate.of(2025, 8, 10));
        reservation.setCheckOutDate(LocalDate.of(2025, 8, 12));
        reservationRepository.save(reservation);

        String conflictJson = "{\"hotelId\":1,\"roomId\":1,\"guestName\":\"John Doe\",\"checkInDate\":\"2025-08-11\",\"checkOutDate\":\"2025-08-13\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(conflictJson))
                .andExpect(status().is4xxClientError());
    }
}

