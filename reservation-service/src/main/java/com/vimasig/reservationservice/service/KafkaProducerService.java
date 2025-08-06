package com.vimasig.reservationservice.service;

import com.vimasig.reservationservice.dto.ReservationEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, ReservationEventDTO> kafkaTemplate;

    @Value("${kafka.topic.reservation.create}")
    private String createTopic;
    @Value("${kafka.topic.reservation.update}")
    private String updateTopic;
    @Value("${kafka.topic.reservation.delete}")
    private String deleteTopic;

    public void sendCreateEvent(ReservationEventDTO eventDTO) {
        kafkaTemplate.send(createTopic, eventDTO);
    }

    public void sendUpdateEvent(ReservationEventDTO eventDTO) {
        kafkaTemplate.send(updateTopic, eventDTO);
    }

    public void sendDeleteEvent(ReservationEventDTO eventDTO) {
        kafkaTemplate.send(deleteTopic, eventDTO);
    }
}
