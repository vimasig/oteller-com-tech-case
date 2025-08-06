package com.vimasig.reservationservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    @NotNull
    private Long hotelId;
    @NotNull
    private Long roomId;
    @NotBlank
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
