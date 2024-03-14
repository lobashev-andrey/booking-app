package com.example.bookingapp.statistics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaUserMessage implements KafkaMessage{

    private Long userId;
}
