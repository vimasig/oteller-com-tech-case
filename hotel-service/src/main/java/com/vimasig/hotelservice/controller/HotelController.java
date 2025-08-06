package com.vimasig.hotelservice.controller;

import com.vimasig.hotelservice.dto.HotelDTO;
import com.vimasig.hotelservice.model.Hotel;
import com.vimasig.hotelservice.service.HotelService;
import com.vimasig.hotelservice.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final HotelMapper hotelMapper;

    @GetMapping
    public List<HotelDTO> getAllHotels() {
        return hotelService.getAllHotels().stream()
                .map(hotelMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable("id") Long id) {
        Optional<Hotel> hotel = hotelService.getHotelById(id);
        return hotel.map(h -> ResponseEntity.ok(hotelMapper.toDTO(h)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public HotelDTO createHotel(@RequestBody HotelDTO hotelDTO) {
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        Hotel savedHotel = hotelService.createHotel(hotel);
        return hotelMapper.toDTO(savedHotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable("id") Long id, @RequestBody HotelDTO hotelDTO) {
        Hotel hotelDetails = hotelMapper.toEntity(hotelDTO);
        Hotel updatedHotel = hotelService.updateHotel(id, hotelDetails);
        if (updatedHotel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(hotelMapper.toDTO(updatedHotel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable("id") Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}
