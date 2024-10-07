package com.alarm.notification.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Setter
@Getter
@Entity
@DiscriminatorValue("NOTICE")
public class NoticeNotification extends Notification {

    @Column(nullable = true)
    private Long postId;

}
