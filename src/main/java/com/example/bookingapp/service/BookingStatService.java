package com.example.bookingapp.service;

import com.example.bookingapp.repository.BookingStatRepository;
import com.example.bookingapp.entity.BookingData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingStatService {

    private final BookingStatRepository repository;

    public Flux<BookingData> findAll() {
        log.debug("findAll() method is called");

        return repository.findAll();
    }

    public Mono<BookingData> create(BookingData bookingData) {
        log.debug("create() method is called");

        bookingData.setId(LocalDateTime.now().toString());

        return repository.save(bookingData);
    }
}
