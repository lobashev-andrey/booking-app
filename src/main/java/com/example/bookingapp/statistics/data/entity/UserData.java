package com.example.bookingapp.statistics.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserData {

    @Id
    private String id;

    private Long userId;


    @Override
    public String toString() {
        return  id.substring(0, 19) +
                ": userId=" + userId;
    }
}
