package com.vimasig.hotelservice;

import com.vimasig.hotelservice.controller.RoomController;
import com.vimasig.hotelservice.model.Room;
import com.vimasig.hotelservice.service.RoomService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomControllerTest {
    @Mock
    private RoomService roomService;

    @InjectMocks
    private RoomController roomController;

    public RoomControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRoomById() {
        Room room = new Room();
        room.setId(1L);
        room.setRoomNumber("101");
        when(roomService.getRoomById(1L)).thenReturn(Optional.of(room));
        ResponseEntity<Room> response = roomController.getRoomById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("101", response.getBody().getRoomNumber());
    }

    @Test
    void testCreateRoom() {
        Room room = new Room();
        room.setRoomNumber("102");
        room.setPricePerNight(BigDecimal.valueOf(100));
        when(roomService.createRoom(any(Room.class))).thenReturn(room);
        ResponseEntity<Room> response = roomController.createRoom(room);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("102", response.getBody().getRoomNumber());
        assertEquals(BigDecimal.valueOf(100), response.getBody().getPricePerNight());
    }
}

