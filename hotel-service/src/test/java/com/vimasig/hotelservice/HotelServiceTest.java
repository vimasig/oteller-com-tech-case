package com.vimasig.hotelservice;

import com.vimasig.hotelservice.model.Hotel;
import com.vimasig.hotelservice.repository.HotelRepository;
import com.vimasig.hotelservice.service.HotelService;
import com.vimasig.hotelservice.service.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServiceTest {
    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private HotelService hotelService;

    public HotelServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetHotelById() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        Optional<Hotel> result = hotelService.getHotelById(1L);
        assertTrue(result.isPresent());
        assertEquals("Test Hotel", result.get().getName());
    }

    @Test
    void testCreateHotel() {
        Hotel hotel = new Hotel();
        hotel.setName("New Hotel");
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        doNothing().when(kafkaProducerService).sendHotelCreatedEvent(any());
        Hotel result = hotelService.createHotel(hotel);
        assertEquals("New Hotel", result.getName());
    }
}
