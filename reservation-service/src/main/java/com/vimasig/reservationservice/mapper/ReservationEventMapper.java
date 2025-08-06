package com.vimasig.reservationservice.mapper;

import com.vimasig.reservationservice.dto.ReservationEventDTO;
import com.vimasig.reservationservice.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationEventMapper {
    ReservationEventMapper INSTANCE = Mappers.getMapper(ReservationEventMapper.class);

    @Mapping(source = "id", target = "reservationId")
    ReservationEventDTO toEventDTO(Reservation reservation);
}
