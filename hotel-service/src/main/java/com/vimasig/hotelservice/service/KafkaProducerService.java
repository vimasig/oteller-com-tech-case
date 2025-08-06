package com.vimasig.hotelservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.hotel.created}")
    private String hotelCreatedTopic;
    @Value("${kafka.topic.hotel.updated}")
    private String hotelUpdatedTopic;
    @Value("${kafka.topic.hotel.deleted}")
    private String hotelDeletedTopic;

    public void sendHotelCreatedEvent(Object event) {
        kafkaTemplate.send(hotelCreatedTopic, event);
    }

    public void sendHotelUpdatedEvent(Object event) {
        kafkaTemplate.send(hotelUpdatedTopic, event);
    }

    public void sendHotelDeletedEvent(Object event) {
        kafkaTemplate.send(hotelDeletedTopic, event);
    }
}
