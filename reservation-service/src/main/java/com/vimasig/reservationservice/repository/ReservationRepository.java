package com.vimasig.reservationservice.repository;

import com.vimasig.reservationservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM Reservation r WHERE r.hotelId = :hotelId AND r.roomId = :roomId AND r.checkOutDate > :checkInDate AND r.checkInDate < :checkOutDate")
    List<Reservation> findOverlappingReservations(@Param("hotelId") Long hotelId, @Param("roomId") Long roomId, @Param("checkInDate") LocalDate checkInDate, @Param("checkOutDate") LocalDate checkOutDate);

    List<Reservation> findByGuestName(String guestName);
}
