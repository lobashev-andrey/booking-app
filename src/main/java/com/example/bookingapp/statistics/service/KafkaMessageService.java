package com.example.bookingapp.statistics.service;

import com.example.bookingapp.statistics.data.BookingStatService;
import com.example.bookingapp.statistics.data.UserStatService;
import com.example.bookingapp.statistics.data.entity.KafkaMapper;
import com.example.bookingapp.statistics.model.KafkaBookingMessage;
import com.example.bookingapp.statistics.model.KafkaMessage;
import com.example.bookingapp.statistics.model.KafkaUserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageService {

    private final UserStatService userStatService;

    private final BookingStatService bookingStatService;

    private final KafkaMapper mapper;


    public void addCreatedUserMessage(KafkaMessage kafkaMessage) {

        userStatService.create(
                mapper.toUserData(
                        (KafkaUserMessage)kafkaMessage )).block();
    }

    public void addBookingMessage(KafkaMessage kafkaMessage) {

        bookingStatService.create(
                mapper.toBookingData(
                        (KafkaBookingMessage) kafkaMessage)).block();
    }
}
