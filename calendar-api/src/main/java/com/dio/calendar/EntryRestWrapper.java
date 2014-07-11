package com.dio.calendar;

import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 7/11/14.
 */

@XmlRootElement
public class EntryRestWrapper implements Serializable {
    private String subject;
    private String description;
    private String startDate;
    private String endDate;
    private List<String> attenders;
    private List<Notification> notifications;
    private String id;

    private static Logger logger = Logger.getLogger(EntryRestWrapper.class);


    public EntryRestWrapper() {
    }

    public EntryRestWrapper(Entry entry) {
        this.subject = entry.getSubject();
        this.description = entry.getDescription();

        this.startDate = EntryApi.dateToString(entry.getStartDate());
        this.endDate = EntryApi.dateToString(entry.getEndDate());
        this.attenders = entry.getAttenders();
        this.notifications = entry.getNotifications();
        this.id = entry.getId().toString();
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
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    @XmlElement
    public void setEndDate(String endDate) {
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
    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public List<String> getAttenders() {
        return attenders;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public String getId() {
        return id;
    }

    public Entry createEntry() {
        Entry entry = null;
        try {
            entry = new Entry.Builder().
                    subject(subject).
                    description(description).
                    startDate(EntryApi.stringToDate(startDate)).
                    endDate(EntryApi.stringToDate(endDate)).
                    attenders(attenders).
                    notifications(notifications).
                    id(UUID.fromString(id)).
                    build();
        } catch (ParseException e) {
            logger.error(e);
        }
        return entry;
    }

}
