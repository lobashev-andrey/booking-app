package com.example.bookingapp.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelFilter {

    private Long hotelId;

    private String name;

    private String announce;

    private String city;

    private String address;

    private Float distance;

    private Float rating;

    private Integer votes;

    private Integer pageSize;

    private Integer pageNumber;
}
