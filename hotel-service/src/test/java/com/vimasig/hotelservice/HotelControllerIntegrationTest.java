package com.vimasig.hotelservice;

import com.vimasig.hotelservice.model.Hotel;
import com.vimasig.hotelservice.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class HotelControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HotelRepository hotelRepository;

    @Test
    void testCreateAndGetHotel() throws Exception {
        String hotelJson = "{\"name\":\"Integration Hotel\",\"address\":\"Test Address\",\"starRating\":4}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(hotelJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Integration Hotel"));

        Hotel hotel = hotelRepository.findAll().get(0);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/hotels/" + hotel.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Integration Hotel"))
                .andExpect(jsonPath("$.address").value("Test Address"))
                .andExpect(jsonPath("$.starRating").value(4));
    }
}

