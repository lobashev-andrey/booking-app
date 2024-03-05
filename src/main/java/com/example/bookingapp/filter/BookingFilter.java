package com.example.bookingapp.filter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingFilter {

    private Integer pageSize = 20;

    private Integer pageNumber = 0;
}
