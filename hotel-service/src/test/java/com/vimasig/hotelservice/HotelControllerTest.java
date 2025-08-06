package com.vimasig.hotelservice;

import com.vimasig.hotelservice.controller.HotelController;
import com.vimasig.hotelservice.dto.HotelDTO;
import com.vimasig.hotelservice.service.HotelService;
import com.vimasig.hotelservice.model.Hotel;
import com.vimasig.hotelservice.mapper.HotelMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelControllerTest {
    @Mock
    private HotelService hotelService;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelController hotelController;

    public HotelControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetHotelById() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        hotel.setAddress("Test Address");
        hotel.setStarRating(5);
        when(hotelService.getHotelById(1L)).thenReturn(Optional.of(hotel));
        HotelDTO hotelDTO = new HotelDTO("Test Hotel", "Test Address", 5);
        when(hotelMapper.toDTO(hotel)).thenReturn(hotelDTO);
        ResponseEntity<HotelDTO> response = hotelController.getHotelById(1L);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Test Hotel", response.getBody().getName());
    }

    @Test
    void testCreateHotel() {
        HotelDTO hotelDTO = new HotelDTO("New Hotel", "New Address", 4);
        Hotel hotel = new Hotel();
        hotel.setName("New Hotel");
        hotel.setAddress("New Address");
        hotel.setStarRating(4);
        when(hotelMapper.toEntity(hotelDTO)).thenReturn(hotel);
        when(hotelService.createHotel(hotel)).thenReturn(hotel);
        when(hotelMapper.toDTO(hotel)).thenReturn(hotelDTO);
        HotelDTO result = hotelController.createHotel(hotelDTO);
        assertNotNull(result);
        assertEquals("New Hotel", result.getName());
        assertEquals("New Address", result.getAddress());
        assertEquals(4, result.getStarRating());
    }
}
