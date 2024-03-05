package com.example.bookingapp.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomFilter {

    private Long roomId;

    private String name;

    private Integer minCost;

    private Integer maxCost;

    private Integer capacity;

    private String arrival;

    private String departure;

    private Long hotelId;

    private Integer pageSize = 20;

    private Integer pageNumber = 0;

}
