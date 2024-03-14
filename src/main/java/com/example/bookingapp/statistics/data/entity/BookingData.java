package com.example.bookingapp.statistics.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bookings")
public class BookingData {

    @Id
    private String id;

    private Long roomId;

    private Long userId;

    private String checkIn;

    private String checkOut;

    @Override
    public String toString() {
        return  id.substring(0, 19) +
                ": roomId=" + roomId +
                ", userId=" + userId +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut;
    }
}
