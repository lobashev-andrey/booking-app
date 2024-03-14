package com.example.bookingapp.statistics.data;

import com.example.bookingapp.statistics.data.entity.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserStatService {

    private final UserStatRepository repository;
    public Flux<UserData> findAll() {
        return repository.findAll();
    }

    public Mono<UserData> create(UserData userData) {

        userData.setId(LocalDateTime.now().toString());

        return repository.save(userData);
    }
}
