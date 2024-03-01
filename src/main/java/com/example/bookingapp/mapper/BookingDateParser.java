package com.example.bookingapp.mapper;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@NoArgsConstructor
public class BookingDateParser {

    public LocalDate parse(String date) {
        return  LocalDate.parse(date);
    }
}
