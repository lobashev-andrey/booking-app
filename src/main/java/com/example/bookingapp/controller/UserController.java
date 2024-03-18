package com.example.bookingapp.controller;

import com.example.bookingapp.dto.UserListResponse;
import com.example.bookingapp.dto.UserRequest;
import com.example.bookingapp.dto.UserResponse;
import com.example.bookingapp.mapper.UserMapper;
import com.example.bookingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;

    private final UserMapper mapper;

    @GetMapping
    public ResponseEntity<UserListResponse> findAll() {
        log.info("findAll() method is called");

        return ResponseEntity.ok(
                mapper.userListToUserListResponse(
                        service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        log.info("findById() method is called with id={}", id);

        return ResponseEntity.ok(
                mapper.userToUserResponse(
                        service.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest request) {
        log.info("update() method is called with id={}", id);

        return ResponseEntity.ok(
                mapper.userToUserResponse(
                        service.update(
                                mapper.userRequestToUser(id, request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("delete() method is called with id={}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
