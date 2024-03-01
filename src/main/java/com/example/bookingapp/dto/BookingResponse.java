package com.example.bookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long roomId;

    private Long userId;

    private LocalDate checkIn;

    private LocalDate checkOut;
}
