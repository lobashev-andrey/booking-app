package com.example.bookingapp.mapper;

import com.example.bookingapp.error.IncorrectRequestException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
@NoArgsConstructor
public class BookingDateParser {

    public LocalDate parse(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException ex) {
            throw new IncorrectRequestException("Неправильный формат даты: " + date);
        }
    }
}
