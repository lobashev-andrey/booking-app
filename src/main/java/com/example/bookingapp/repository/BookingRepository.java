package com.example.bookingapp.repository;

import com.example.bookingapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM bookings b WHERE b.room.id = :id " +
            "AND (" +
            "(b.checkIn BETWEEN :start AND :end) OR " +
            "(b.checkOut BETWEEN :start AND :end) OR" +
            "(b.checkIn < :start AND b.checkOut > :end )" +
            ")")
    List<Booking> checkBeforeBooking(@Param("id") Long roomId, @Param("start") LocalDate checkIn, @Param("end") LocalDate checkOut);
}
