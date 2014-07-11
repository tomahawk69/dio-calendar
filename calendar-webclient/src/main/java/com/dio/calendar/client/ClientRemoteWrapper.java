package com.dio.calendar.client;

import com.dio.calendar.CalendarEntryBadAttribute;
import com.dio.calendar.CalendarKeyViolation;
import com.dio.calendar.Entry;
import com.dio.calendar.Notification;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by yur on 11.07.2014.
 */
public interface ClientRemoteWrapper {

    Entry editSubject(Entry oldEntry, String subject);

    Entry editDescription(Entry oldEntry, String description);

    Entry editStartDate(Entry oldEntry, Date startDate);

    Entry editEndDate(Entry oldEntry, Date endDate);

    Entry editAttenders(Entry oldEntry, List<String> attenders);

    Entry editNotifications(Entry oldEntry, List<Notification> notifications);

    Entry newEntry(String subject, String description, Date startDate, Date endDate, List<String> attenders, List<Notification> notifications);

    Entry addEntry(Entry entry) throws CalendarEntryBadAttribute, CalendarKeyViolation;

    void removeEntryById(UUID id);

    Entry updateEntry(Entry newEntry, Entry oldEntry) throws CalendarEntryBadAttribute;

    Entry updateEntry(Entry newEntry) throws CalendarEntryBadAttribute;

    void removeEntry(Entry entry);

    Entry copyEntry(Entry entry);

    Entry getEntry(UUID id);

    List<Entry> getEntriesBySubject(String subject);

    List<Entry> getEntriesByAttender(String attender);

    List<Entry> getEntries();

    void clearData();

}
