package com.example.bookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    private String name;

    private String description;

    private String number;

    private Integer price;

    private Integer capacity;

    private Long hotelId;
}
