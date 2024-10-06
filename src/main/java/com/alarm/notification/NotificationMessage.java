package com.alarm.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationMessage {
    private Long employeeId;
    private String message;
}
