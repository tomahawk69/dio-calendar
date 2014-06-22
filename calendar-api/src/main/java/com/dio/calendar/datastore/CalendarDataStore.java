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

    public Entry removeEntry(UUID id) throws DataStoreFSException;
    ArrayList<Entry> getEntries();
    Collection<Notification> getNotifications();
    boolean entryExists(UUID id);
    Entry getEntry(UUID id);

    void init() throws IOException;
    void loadData() throws IOException;
    void clearData() throws IOException, DataStoreFSException;

    List<Entry> getEntryByAttender(String attender);
    List<Entry> getEntryBySubject(String subject);

    Entry addEntry(Entry entry) throws CalendarKeyViolation, DataStoreFSException;
    void addEntryToEntries(Entry entry);
    Entry updateEntry(Entry newEntry, Entry oldEntry) throws DataStoreFSException;

    void setLoadDone();

    boolean getIsLoad();
}
