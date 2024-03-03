package com.example.bookingapp.controller;

import com.example.bookingapp.dto.UserListResponse;
import com.example.bookingapp.dto.UserRequest;
import com.example.bookingapp.dto.UserResponse;
import com.example.bookingapp.mapper.UserMapper;
import com.example.bookingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    private final UserMapper mapper;

    @GetMapping
    public ResponseEntity<UserListResponse> findAll() {
        return ResponseEntity.ok(
                mapper.userListToUserListResponse(
                        service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                mapper.userToUserResponse(
                        service.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest request) {
        return ResponseEntity.ok(
                mapper.userToUserResponse(
                        service.update(
                                mapper.userRequestToUser(id, request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
