package com.example.bookingapp.repository;

import com.example.bookingapp.entity.UserData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserStatRepository extends ReactiveMongoRepository<UserData, Long> {
}
