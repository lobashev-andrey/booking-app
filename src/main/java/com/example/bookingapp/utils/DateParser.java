package com.example.bookingapp.utils;

import com.example.bookingapp.error.IncorrectRequestException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Component
@NoArgsConstructor
@Slf4j
public class DateParser {

    public static LocalDate parse(String date) {
        log.debug("parse() method is called");
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException ex) {
            throw new IncorrectRequestException("Неправильный формат даты: " + date);
        }
    }
}