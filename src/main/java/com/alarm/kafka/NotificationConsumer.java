package com.alarm.kafka;

import com.alarm.notification.NotificationMessage;
import com.alarm.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "approval-topic", groupId = "group_1")
    public void consume(NotificationMessage message) {
        // 메시지를 수신하고 > 알림 서비스로
        notificationService.saveNotification(message);
        // 알림 전송
        notificationService.sendRealTimeNotification(message);
    }
}