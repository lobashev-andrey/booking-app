package com.example.bookingapp.service;

import com.example.bookingapp.repository.UserStatRepository;
import com.example.bookingapp.entity.UserData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStatService {

    private final UserStatRepository repository;
    public Flux<UserData> findAll() {
        log.debug("findAll() method is called");
        return repository.findAll();
    }

    public Mono<UserData> create(UserData userData) {
        log.debug("create() method is called");

        userData.setId(LocalDateTime.now().toString());

        return repository.save(userData);
    }
}
