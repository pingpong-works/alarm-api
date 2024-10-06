package com.alarm.notification;

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
        // 알림을 DB에 저장
        Notification notification = new Notification();


        notificationRepository.save(notification);
    }

    public void sendRealTimeNotification(NotificationMessage message) {
        // SSE로 실시간 알림 전송
        sseEmitterService.sendToUser(message.getEmployeeId(), message);
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
        notificationRepository.deleteAllReadBefore(LocalDateTime.now().minusDays(30));
    }

    private Notification findVerifiedNotification (Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(RuntimeException::new);
    }
}
