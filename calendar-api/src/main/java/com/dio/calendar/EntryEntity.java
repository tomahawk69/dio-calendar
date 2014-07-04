package com.dio.calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 7/4/2014.
 */

@Entity
@Table(name="entries")
public class EntryEntity {
    private String subject;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<String> attenders;
    private List<Notification> notifications;
    private UUID id;


    public EntryEntity() {
    }

    public EntryEntity(Entry entry) {
        this.subject = entry.getSubject();
        this.description = entry.getDescription();
        this.startDate = entry.getStartDate();
        this.endDate = entry.getEndDate();
        this.attenders = entry.getAttenders();
        this.notifications = entry.getNotifications();
        this.id = entry.getId();
    }

    @Id
    @Column(name="f_id")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Column(name="f_subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(name="f_description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="f_dateFrom")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name="f_dateTo")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setAttenders(List<String> attenders) {
        this.attenders = attenders;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<String> getAttenders() {
        return attenders;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public Entry createEntry() {
        return new Entry.Builder().
                subject(subject).
                description(description).
                startDate(startDate).
                endDate(endDate).
                attenders(attenders).
                notifications(notifications).
                id(id).
                build();
    }

}