package com.example.bookingapp.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HotelFilter {

    private Long hotelId;

    private String name;

    private String announce;

    private String city;

    private String address;

    private Float distance;

    private Float rating;

    private Integer votes;

    private Integer pageSize = 20;

    private Integer pageNumber = 0;
}
