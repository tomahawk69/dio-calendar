package com.dio.calendar;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by yur on 06.07.2014.
 */

@Entity
@Table(name = "entry_notifications")
public class NotificationEntity implements Serializable {

    private Long notificationId;

    private EntryEntity entryEntity;
    private EnumNotificationType type;
    private EnumNotificationPeriod period;
    private Integer offset;

    public NotificationEntity() {
    }

    public NotificationEntity(EntryEntity entryEntity, EnumNotificationType type, EnumNotificationPeriod period, Integer offset) {
        this.entryEntity = entryEntity;
        this.type = type;
        this.period = period;
        this.offset = offset;
    }

    @Id
    @GeneratedValue
    @Column(name="f_notification_id")
    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    @ManyToOne
    @JoinColumn(name="entry_id")
    public EntryEntity getEntryEntity() {
        return entryEntity;
    }

    public void setEntryEntity(EntryEntity entryEntity) {
        this.entryEntity = entryEntity;
    }

    public void setType(EnumNotificationType type) {
        this.type = type;
    }

    public void setPeriod(EnumNotificationPeriod period) {
        this.period = period;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public EnumNotificationType getType() {
        return type;
    }

    public EnumNotificationPeriod getPeriod() {
        return period;
    }

    public Integer getOffset() {
        return offset;
    }

    private String id;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
