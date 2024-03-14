package com.example.bookingapp.statistics.data;

import com.example.bookingapp.statistics.data.entity.BookingData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingStatService {

    private final BookingStatRepository repository;

    public Flux<BookingData> findAll() {
        return repository.findAll();
    }

    public Mono<BookingData> create(BookingData bookingData) {

        bookingData.setId(LocalDateTime.now().toString());

        return repository.save(bookingData);
    }
}
