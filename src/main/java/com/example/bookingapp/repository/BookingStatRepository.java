package com.example.bookingapp.repository;

import com.example.bookingapp.entity.BookingData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BookingStatRepository extends ReactiveMongoRepository<BookingData, Long> {
}
