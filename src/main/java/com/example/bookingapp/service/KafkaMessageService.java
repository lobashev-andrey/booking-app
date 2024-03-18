package com.example.bookingapp.service;

import com.example.bookingapp.mapper.KafkaMapper;
import com.example.bookingapp.dto.KafkaBookingMessage;
import com.example.bookingapp.dto.KafkaMessage;
import com.example.bookingapp.dto.KafkaUserMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageService {

    private final UserStatService userStatService;

    private final BookingStatService bookingStatService;

    private final KafkaMapper mapper;


    public void addCreatedUserMessage(KafkaMessage kafkaMessage) {
        log.info("addCreatedUserMessage() method is called");

        userStatService.create(
                mapper.toUserData(
                        (KafkaUserMessage)kafkaMessage )).block();
    }

    public void addBookingMessage(KafkaMessage kafkaMessage) {
        log.info("addBookingMessage() method is called");

        bookingStatService.create(
                mapper.toBookingData(
                        (KafkaBookingMessage) kafkaMessage)).block();
    }
}
