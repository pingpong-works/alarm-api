package com.alarm.notification;

import com.alarm.notification.entity.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    default NotificationResponse notificationToResponse (Notification notification) {
        NotificationResponse response = NotificationResponse.builder()
                .id(notification.getId())
                .createdAt(notification.getCreatedAt())
                .employeeId(notification.getEmployeeId())
                .isRead(notification.isRead())
                .message(notification.getMessage())
                .notificationType(notification.getClass().getTypeName())
                .build();

        if (notification instanceof ApprovalNotification) {
            ApprovalNotification approvalNotification = (ApprovalNotification) notification;
            response.setApprovalId(approvalNotification.getApprovalId());
        } else if (notification instanceof BookCarNotification) {
            BookCarNotification bookCarNotification = (BookCarNotification) notification;
            response.setBookCarId(bookCarNotification.getBookCarId());
        } else if (notification instanceof BookRoomNotification) {
            BookRoomNotification bookRoomNotification = (BookRoomNotification) notification;
            response.setBookRoomId(bookRoomNotification.getBookRoomId());
        } else if (notification instanceof CalendarNotification) {
            CalendarNotification calendarNotification = (CalendarNotification) notification;
            response.setCalendarId(calendarNotification.getCalendarId());
        } else if (notification instanceof DocumentNotification) {
            DocumentNotification documentNotification = (DocumentNotification) notification;
            response.setDocumentId(documentNotification.getDocumentId());
        } else if (notification instanceof NoticeNotification) {
            NoticeNotification postNotification = (NoticeNotification) notification;
            response.setPostId(postNotification.getPostId());
        }

        return response;
    }

    List<NotificationResponse> notificationsToResponses (List<Notification> notifications);

}
