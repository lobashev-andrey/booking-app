package com.example.bookingapp.mapper;

import com.example.bookingapp.dto.UserListResponse;
import com.example.bookingapp.dto.UserRequest;
import com.example.bookingapp.dto.UserResponse;
import com.example.bookingapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User userRequestToUser(UserRequest request);    // PasswordEncoder !!!!!!!!!!!!

    @Mapping(source = "id", target = "id")
    User userRequestToUser(Long id, UserRequest request);

    UserResponse userToUserResponse(User user); // PasswordEncoder.decode() !!!!!!!!

    default UserListResponse userListToUserListResponse(List<User> users) {
        return  new UserListResponse(
                users.stream().map(
                        this::userToUserResponse).toList());
    }
}
