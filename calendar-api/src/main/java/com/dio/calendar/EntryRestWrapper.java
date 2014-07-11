package com.dio.calendar;

import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.text.DateFormat;
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

    private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    private static Logger logger = Logger.getLogger(EntryRestWrapper.class);


    public EntryRestWrapper() {
    }

    public EntryRestWrapper(Entry entry) {
        this.subject = entry.getSubject();
        this.description = entry.getDescription();

        this.startDate = dateToString(entry.getStartDate());
        this.endDate = dateToString(entry.getEndDate());
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
        return new Entry.Builder().
                subject(subject).
                description(description).
                startDate(stringToDate(startDate)).
                endDate(stringToDate(endDate)).
                attenders(attenders).
                notifications(notifications).
                id(UUID.fromString(id)).
                build();
    }

    public String dateToString(Date date) {
        if (date == null) {
            return null;
        } else {
            return df.format(date);
        }
    }

    private Date stringToDate(String string) {
        Date result = null;
        if (string != null) {
            try {
                result = df.parse(string);
            }
            catch (Exception e) {
                logger.error(String.format("Wrong String '%s' provided to encode into the Date", string));
            }

        }
        return result;
    }
}
