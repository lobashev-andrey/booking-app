package com.example.bookingapp.controller;

import com.example.bookingapp.dto.UserRequest;
import com.example.bookingapp.dto.UserResponse;
import com.example.bookingapp.mapper.UserMapper;
import com.example.bookingapp.service.UserService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final UserService service;

    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request, @PathParam("role") String role) {
        log.info("create() method is called with role={}", role);
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        return ResponseEntity.status(HttpStatus.CREATED).body(
                mapper.userToUserResponse(
                        service.create(
                                mapper.userRequestToUser(request),  role)));
    }
}
