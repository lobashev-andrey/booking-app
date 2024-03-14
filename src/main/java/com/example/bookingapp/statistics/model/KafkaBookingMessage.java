package com.example.bookingapp.statistics.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KafkaBookingMessage implements KafkaMessage{

    private Long roomId;

    private Long userId;

    private String checkIn;

    private String checkOut;
}
