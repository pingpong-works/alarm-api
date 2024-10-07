package com.alarm.notification;

import com.alarm.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    void deleteByIsReadTrueAndCreatedAtBefore(LocalDateTime time);

    List<Notification> findByEmployeeIdAndIsRead(Long employeeId, boolean isRead);
}
