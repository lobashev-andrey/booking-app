package com.example.bookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    private Long roomId;

    private Long userId;

    private String checkIn;

    private String checkOut;
}
