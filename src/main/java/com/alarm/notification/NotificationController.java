package com.alarm.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final SseEmitterService sseEmitterService;

    // 알림 목록 조회 (읽은/안 읽은 필터링 가능)
    @GetMapping("/{employee-id}")
    public ResponseEntity getNotifications(
            @PathVariable("employee-id") Long employeeId,
            @RequestParam(required = false, defaultValue = "false") boolean isRead) {
        List<Notification> notifications = notificationService.getUserNotifications(employeeId, isRead);
        return ResponseEntity.ok(notifications);
    }

    // 알림 읽음 처리
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }

    // 실시간 알림 수신(SSE 연결 시작)
    @GetMapping("/subscribe/{employee-id}")
    public SseEmitter subscribe(@PathVariable("employee-id") Long employeeId) {
        return sseEmitterService.createEmitter(employeeId);
    }

    // 30일 지난 알림 삭제
    @DeleteMapping("/old")
    public ResponseEntity deleteOldNotifications() {
        notificationService.deleteOldNotifications();
        return ResponseEntity.noContent().build();
    }

}
