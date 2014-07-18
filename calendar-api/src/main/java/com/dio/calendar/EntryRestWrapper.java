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

//    private static Logger logger = Logger.getLogger(EntryRestWrapper.class);


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
//            logger.error(e);
            throw new RuntimeException(e);
}
        return entry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntryRestWrapper that = (EntryRestWrapper) o;

        if (attenders != null ? !attenders.equals(that.attenders) : that.attenders != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (notifications != null ? !notifications.equals(that.notifications) : that.notifications != null)
            return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = subject != null ? subject.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (attenders != null ? attenders.hashCode() : 0);
        result = 31 * result + (notifications != null ? notifications.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EntryRestWrapper{" +
                "subject='" + subject + '\'' +
                ", description='" + description + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", attenders=" + attenders +
                ", notifications=" + notifications +
                ", id='" + id + '\'' +
                '}';
    }
}
