package com.alarm.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    void deleteAllReadBefore(LocalDateTime localDateTime);

    List<Notification> findByEmployeeIdAndIsRead(Long employeeId, boolean isRead);
}
