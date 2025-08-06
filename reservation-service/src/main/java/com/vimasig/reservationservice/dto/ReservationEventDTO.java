package com.vimasig.reservationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEventDTO {
    private Long reservationId;
    private Long hotelId;
    private Long roomId;
    private String guestName;
    private String checkInDate;
    private String checkOutDate;
}

