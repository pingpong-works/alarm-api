package com.alarm.kafka;

import com.alarm.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "approval-topic", groupId = "alarm-api")
    public void consume(NotificationMessage message) {
        // 메시지를 수신하고 저장
        notificationService.saveNotification(message);
        // 알림 전송
        notificationService.sendRealTimeNotification(message);
    }

    @KafkaListener(topics = "document-topic", groupId = "alarm-api")
    public void consumeDocument(NotificationMessage message) {

        notificationService.saveNotification(message);
        notificationService.sendRealTimeNotification(message);
    }

    @KafkaListener(topics = "notice-topic", groupId = "alarm-api")
    public void consumeNotice(NotificationMessage message) {

        notificationService.saveNotification(message);
        notificationService.sendRealTimeNotification(message);
    }

    @KafkaListener(topics = "book-car-topic", groupId = "alarm-api")
    public void consumeBookCar (NotificationMessage message) {

        notificationService.saveNotification(message);
        notificationService.sendRealTimeNotification(message);
    }

    @KafkaListener(topics = "book-room-topic", groupId = "alarm-api")
    public void consumeBookRoom (NotificationMessage message) {

        notificationService.saveNotification(message);
        notificationService.sendRealTimeNotification(message);
    }

    @KafkaListener(topics = "calendar-topic", groupId = "alarm-api")
    public void consumeCalendar (NotificationMessage message) {

        notificationService.saveNotification(message);
        notificationService.sendRealTimeNotification(message);
    }
}