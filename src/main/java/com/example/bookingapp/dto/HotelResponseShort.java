package com.example.bookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseShort {

    private Long id;

    private String name;

    private String announce;

    private String address;

    private Float distance;
}
