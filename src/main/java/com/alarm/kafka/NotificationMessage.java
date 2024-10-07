package com.alarm.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessage {
    private Long employeeId;
    private String message;
    private Long typeId;
    private NotificationType type;

    public enum NotificationType {
        APPROVAL,
        NOTICE,
        CHAT,
        EMAIL,
        DOCUMENT;
    }
}
