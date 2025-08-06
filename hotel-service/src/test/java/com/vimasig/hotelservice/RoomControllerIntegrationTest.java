package com.vimasig.hotelservice;

import com.vimasig.hotelservice.model.Hotel;
import com.vimasig.hotelservice.model.Room;
import com.vimasig.hotelservice.repository.HotelRepository;
import com.vimasig.hotelservice.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RoomControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Hotel hotel;

    @BeforeEach
    void setup() {
        hotel = new Hotel();
        hotel.setName("RoomTestHotel");
        hotel.setAddress("RoomTestAddress");
        hotel.setStarRating(3);
        hotel = hotelRepository.save(hotel);
    }

    @Test
    void testCreateAndGetRoom() throws Exception {
        String roomJson = String.format("{\"hotelId\":%d,\"roomNumber\":\"201\",\"capacity\":2,\"pricePerNight\":150}", hotel.getId());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(roomJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomNumber").value("201"))
                .andExpect(jsonPath("$.capacity").value(2))
                .andExpect(jsonPath("$.pricePerNight").value(150.0));

        Room room = roomRepository.findAll().get(0);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/rooms/" + room.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomNumber").value("201"))
                .andExpect(jsonPath("$.capacity").value(2))
                .andExpect(jsonPath("$.pricePerNight").value(150.0));
    }
}

