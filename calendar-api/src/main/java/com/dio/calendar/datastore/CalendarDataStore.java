package com.dio.calendar.datastore;

import com.dio.calendar.CalendarKeyViolation;
import com.dio.calendar.Entry;
import com.dio.calendar.Notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by yur on 07.05.2014.
 */
public interface CalendarDataStore {

    void init();
    void loadData();
    void clearData() throws DataStoreFSException;

    List<Entry> getEntries();
    List<Entry> getEntryByAttender(String attender);
    List<Entry> getEntryBySubject(String subject);

    List<Notification> getNotifications();

    Entry getEntry(UUID id);
    Entry addEntry(Entry entry) throws CalendarKeyViolation, DataStoreFSException;
    void addEntryToEntries(Entry entry);
    Entry updateEntry(Entry newEntry, Entry oldEntry) throws DataStoreFSException;
    Entry removeEntry(UUID id) throws DataStoreFSException;

    boolean entryExists(UUID id);

    void setLoadDone();

    boolean getIsLoad();
}
