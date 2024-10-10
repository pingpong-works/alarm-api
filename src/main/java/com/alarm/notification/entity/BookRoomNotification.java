package com.alarm.notification.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("BookRoom")
public class BookRoomNotification extends Notification {

    @Column(nullable = true)
    private Long bookRoomId;
}