package com.vimasig.hotelservice;

import com.vimasig.hotelservice.model.Room;
import com.vimasig.hotelservice.repository.RoomRepository;
import com.vimasig.hotelservice.service.RoomService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceTest {
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    public RoomServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRoomById() {
        Room room = new Room();
        room.setId(1L);
        room.setRoomNumber("101");
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        Optional<Room> result = roomService.getRoomById(1L);
        assertTrue(result.isPresent());
        assertEquals("101", result.get().getRoomNumber());
    }

    @Test
    void testCreateRoom() {
        Room room = new Room();
        room.setRoomNumber("102");
        room.setPricePerNight(BigDecimal.valueOf(100));
        when(roomRepository.save(any(Room.class))).thenReturn(room);
        Room result = roomService.createRoom(room);
        assertEquals("102", result.getRoomNumber());
        assertEquals(BigDecimal.valueOf(100), result.getPricePerNight());
    }
}

