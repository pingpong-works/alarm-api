package com.alarm.notification.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NotificationResponse {
    private Long id;
    private String notificationType;
    private LocalDateTime createdAt;
    private Long employeeId;
    private boolean isRead;
    private String message;
    private Long approvalId;
    private Long bookCarId;
    private Long bookRoomId;
    private Long calendarId;
    private Long documentId;
    private Long postId;
}
