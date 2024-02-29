package com.example.bookingapp.dto;

import com.example.bookingapp.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;

    private String name;

    private String password;

    private String email;

    private UserRole role;
}


