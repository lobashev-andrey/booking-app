package com.example.bookingapp.listener;

import com.example.bookingapp.dto.KafkaMessage;
import com.example.bookingapp.service.KafkaMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageListener {

    private final KafkaMessageService kafkaMessageService;

    @KafkaListener(topics = "${app.kafka.kafka-user-topic}",
                   groupId = "${app.kafka.kafka-message-group-id}",
                   containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory")
    public void listenUserCreation(KafkaMessage message) {
        log.info("KafkaMessageListener: listenUserCreation() method is called");

        kafkaMessageService.addCreatedUserMessage(message);
    }

    @KafkaListener(topics = "${app.kafka.kafka-booking-topic}",
            groupId = "${app.kafka.kafka-message-group-id}",
            containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory")
    public void listenBooking(KafkaMessage message) {
        log.info("KafkaMessageListener: listenBooking() method is called");

        kafkaMessageService.addBookingMessage(message);
    }
}
