package com.vimasig.hotelservice.service;

import com.vimasig.hotelservice.model.Hotel;
import com.vimasig.hotelservice.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final KafkaProducerService kafkaProducerService;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    @Transactional
    public Hotel createHotel(Hotel hotel) {
        Hotel savedHotel = hotelRepository.save(hotel);
        kafkaProducerService.sendHotelCreatedEvent(savedHotel);
        return savedHotel;
    }

    @Transactional
    public Hotel updateHotel(Long id, Hotel hotelDetails) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isEmpty()) {
            return null;
        }
        Hotel hotel = optionalHotel.get();
        hotel.setName(hotelDetails.getName());
        hotel.setAddress(hotelDetails.getAddress());
        hotel.setStarRating(hotelDetails.getStarRating());
        Hotel updatedHotel = hotelRepository.save(hotel);
        kafkaProducerService.sendHotelUpdatedEvent(updatedHotel);
        return updatedHotel;
    }

    @Transactional
    public void deleteHotel(Long id) {
        hotelRepository.findById(id).ifPresent(hotel -> {
            hotelRepository.delete(hotel);
            kafkaProducerService.sendHotelDeletedEvent(hotel);
        });
    }
}
