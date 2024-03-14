package com.example.bookingapp.statistics.listener;

import com.example.bookingapp.statistics.model.KafkaMessage;
import com.example.bookingapp.statistics.service.KafkaMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final KafkaMessageService kafkaMessageService;

    @KafkaListener(topics = "${app.kafka.kafka-user-topic}",
                   groupId = "${app.kafka.kafka-message-group-id}",
                   containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory")
    public void listenUserCreation(KafkaMessage message) {
        kafkaMessageService.addCreatedUserMessage(message);
    }

    @KafkaListener(topics = "${app.kafka.kafka-booking-topic}",
            groupId = "${app.kafka.kafka-message-group-id}",
            containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory")
    public void listenBooking(KafkaMessage message) {
        kafkaMessageService.addBookingMessage(message);
    }
}
