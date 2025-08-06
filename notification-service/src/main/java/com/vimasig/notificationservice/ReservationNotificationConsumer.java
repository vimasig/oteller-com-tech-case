package com.vimasig.notificationservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReservationNotificationConsumer {

    @KafkaListener(topics = "reservation-created", groupId = "notification-service-group")
    public void listenReservationEvents(String message) {
        log.info("Received reservation event: {}", message); // or email
    }
}
