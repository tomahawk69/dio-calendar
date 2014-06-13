package com.dio.calendar;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 5/21/14.
 */
@XmlRootElement
public class EntryWrapper implements Serializable{
    private String subject;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<String> attenders;
    private List<Notification> notifications;
    private UUID id;


    public EntryWrapper() {
    }

    public EntryWrapper(Entry entry) {
        this.subject = entry.getSubject();
        this.description = entry.getDescription();
        this.startDate = entry.getStartDate();
        this.endDate = entry.getEndDate();
        this.attenders = entry.getAttenders();
        this.notifications = entry.getNotifications();
        this.id = entry.getId();
    }

    @XmlElement
    public void setSubject(String subject) {
        this.subject = subject;
    }
    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }
    @XmlElement
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    @XmlElement
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    @XmlElement
    public void setAttenders(List<String> attenders) {
        this.attenders = attenders;
    }
    @XmlElement
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
    @XmlElement
    public void setId(UUID id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<String> getAttenders() {
        return attenders;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public UUID getId() {
        return id;
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
