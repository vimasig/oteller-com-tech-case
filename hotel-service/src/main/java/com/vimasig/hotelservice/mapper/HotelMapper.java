package com.vimasig.hotelservice.mapper;

import com.vimasig.hotelservice.dto.HotelDTO;
import com.vimasig.hotelservice.model.Hotel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    HotelDTO toDTO(Hotel hotel);
    Hotel toEntity(HotelDTO hotelDTO);
}

