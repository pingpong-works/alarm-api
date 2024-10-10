package com.alarm.notification;

import com.alarm.kafka.NotificationMessage;
import com.alarm.notification.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SseEmitterService sseEmitterService;

    public void saveNotification(NotificationMessage message) {
        Notification notification = createNotificationEntity(message);
        notificationRepository.save(notification);
    }

    private Notification createNotificationEntity(NotificationMessage message) {
        Notification notification;

        switch (message.getType()) {
            case APPROVAL:
                notification = new ApprovalNotification();
                ((ApprovalNotification) notification).setApprovalId(message.getTypeId());
                break;
            case NOTICE:
                notification = new NoticeNotification();
                ((NoticeNotification) notification).setPostId(message.getTypeId());
                break;
            case DOCUMENT:
                notification = new DocumentNotification();
                ((DocumentNotification) notification).setDocumentId(message.getTypeId());
                break;
            case BOOK_CAR:
                notification = new BookCarNotification();
                ((BookCarNotification) notification).setBookCarId(message.getTypeId());
                break;
            case BOOK_ROOM:
                notification = new BookRoomNotification();
                ((BookRoomNotification) notification).setBookRoomId(message.getTypeId());
                break;
            case CALENDAR:
                notification = new CalendarNotification();
                ((CalendarNotification) notification).setCalendarId(message.getTypeId());
                break;
            default:
                throw new IllegalArgumentException("Unknown notification type: " + message.getType());
        }

        notification.setMessage(message.getMessage());
        notification.setEmployeeId(message.getEmployeeId());

        return notification;
    }

    public void sendRealTimeNotification(NotificationMessage message) {
        // SSE로 실시간 알림 전송
        sseEmitterService.sendToEmployee(message.getEmployeeId(), message);
    }

    public List<Notification> getUserNotifications(Long employeeId, boolean isRead) {
        // 사용자 알림 조회, 읽은/안 읽은 필터링
        return notificationRepository.findByEmployeeIdAndIsRead(employeeId, isRead);
    }

    public void markAsRead(Long notificationId) {
        // 알림 읽음 처리
        Notification notification = findVerifiedNotification(notificationId);
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldNotifications() {
        // 30일 지난 읽은 알림 삭제
        notificationRepository.deleteByIsReadTrueAndCreatedAtBefore(LocalDateTime.now().minusDays(30));
    }

    private Notification findVerifiedNotification (Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(RuntimeException::new);
    }
}
