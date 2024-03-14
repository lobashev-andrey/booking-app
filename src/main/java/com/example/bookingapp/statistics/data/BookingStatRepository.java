package com.example.bookingapp.statistics.data;

import com.example.bookingapp.statistics.data.entity.BookingData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BookingStatRepository extends ReactiveMongoRepository<BookingData, Long> {
}
