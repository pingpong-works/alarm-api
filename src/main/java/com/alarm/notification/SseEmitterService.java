package com.alarm.notification;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseEmitterService {

    // 사용자별로 SseEmitter를 저장하는 맵 (동시성을 위해 ConcurrentHashMap 사용)
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    // 새로운 SSE 연결 생성 및 저장
    public SseEmitter createEmitter(Long employeeId) {
        SseEmitter emitter = new SseEmitter(60 * 1000L);  // 타임아웃 1분 (필요에 따라 조정 가능)

        // 타임아웃 발생 시 Emitter 제거
        emitter.onTimeout(() -> emitters.remove(employeeId));

        // 에러 발생 시 Emitter 제거
        emitter.onError(e -> emitters.remove(employeeId));

        emitters.put(employeeId, emitter);

        return emitter;
    }

    // 특정 사용자에게 실시간 알림 전송
    public void sendToUser(Long employeeId, NotificationMessage message) {
        SseEmitter emitter = emitters.get(employeeId);

        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(message));  // 메시지 전송

            } catch (IOException e) {
                // 전송 중 에러 발생 시 해당 Emitter 제거
                emitters.remove(employeeId);
            }
        }
    }

    // 사용자가 연결을 닫을 때 Emitter 제거
    public void removeEmitter(Long employeeId) {
        emitters.remove(employeeId);
    }
}