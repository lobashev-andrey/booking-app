package com.example.bookingapp.statistics.data;

import com.example.bookingapp.statistics.data.entity.UserData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserStatRepository extends ReactiveMongoRepository<UserData, Long> {
}
