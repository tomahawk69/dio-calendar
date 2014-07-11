package com.dio.calendar;



import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * Class for calendar entry
 *
 * spring
 *+ @not null
 * Main class
 *
 * tests
 * interfaces
 *
 *
 *
 * Created by yur on 29.04.2014.
 */
@XmlRootElement
public class Entry implements Serializable {

    private String subject;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<String> attenders;
    private List<Notification> notifications;
    private UUID id;

//    public Entry() {
//    }

    public UUID getId() { return id; }

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

    private Entry(Builder builder) {
        this.subject = builder.subject;
        this.description = builder.description;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.attenders = builder.attenders;
        this.notifications = builder.notifications;
        this.id = builder.id;
    }

//    public Entry() { }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Entry{");
        sb.append("subject='").append(subject).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", attenders=").append(attenders);
        sb.append(", notifications=").append(notifications);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (attenders != null ? !attenders.equals(entry.attenders) : entry.attenders != null) return false;
        if (description != null ? !description.equals(entry.description) : entry.description != null) return false;
        if (endDate != null ? !endDate.equals(entry.endDate) : entry.endDate != null) return false;
        if (!id.equals(entry.id)) return false;
        if (notifications != null ? !notifications.equals(entry.notifications) : entry.notifications != null)
            return false;
        if (startDate != null ? !startDate.equals(entry.startDate) : entry.startDate != null) return false;
        if (subject != null ? !subject.equals(entry.subject) : entry.subject != null) return false;

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
        result = 31 * result + id.hashCode();
        return result;
    }

    // Builder
    public static class Builder {
        private String subject;
        private String description;
        private Date startDate;
        private Date endDate;
        private List<String> attenders;
        private List<Notification> notifications;
        private UUID id;

        public  Builder() {
            this.attenders = new ArrayList<>();
            this.notifications = new ArrayList<>();
        }

        public Builder(Entry oldEntry) {
            this.subject = oldEntry.getSubject();
            this.description = oldEntry.getDescription();
            this.startDate = oldEntry.getStartDate();
            this.endDate = oldEntry.getEndDate();
            this.attenders = new ArrayList<>(oldEntry.getAttenders());
            this.notifications = new ArrayList<>(oldEntry.getNotifications());
            this.id = oldEntry.getId();
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder attenders(List<String> attenders) {
            if (attenders == null) {
                this.attenders = new ArrayList<>();
            } else {
                this.attenders = attenders;
            }
            return this;
        }

        public Builder attenders(String... attenders) {
            if (this.attenders == null) {
                this.attenders = Arrays.asList(attenders);
            } else {
                this.attenders.addAll(Arrays.asList(attenders));
            }
            return this;
        }

        public Builder notifications(List<Notification> notifications) {
            if (notifications == null) {
                this.notifications = new ArrayList<>();
            } else {
                this.notifications = notifications;
            }
            return this;
        }

        public Entry build() {
            String exceptionMessage = "Entry bad attribute: %s";
            if (id == null)
                id = UUID.randomUUID();

            // в отдельный класс валидацию

/*
            if (startDate == null)
                throw new CalendarEntryBadAttribute(String.format(exceptionMessage, "startDate"));
            if (endDate == null)
                throw new CalendarEntryBadAttribute(String.format(exceptionMessage, "endDate"));
            if (subject == null)
                throw new CalendarEntryBadAttribute(String.format(exceptionMessage, "subject"));
            if (description == null)
                throw new CalendarEntryBadAttribute(String.format(exceptionMessage, "description"));
            if (attenders == null)
                throw new CalendarEntryBadAttribute(String.format(exceptionMessage, "attenders"));
            if (notifications == null)
                throw new CalendarEntryBadAttribute(String.format(exceptionMessage, "notifications"));
            if (endDate.compareTo(startDate) < 0)
                throw new CalendarEntryBadAttribute(String.format(exceptionMessage, "endDate lesser then startDate"));
*/

            return new Entry(this);
        }

    }

}

